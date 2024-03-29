package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeDao tradeDao;
    private final OfferDao offerDao;
    private final UserService userService;
    private final MessageSenderFacade messageSenderFacade;
    private final ChatService chatService;

    @Autowired
    public TradeServiceImpl(TradeDao tradeDao, OfferDao offerDao, MessageSenderFacade messageSenderFacade, UserService userService, ChatService chatService) {
        this.tradeDao = tradeDao;
        this.offerDao = offerDao;
        this.messageSenderFacade = messageSenderFacade;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    @Secured("ROLE_USER")
    @Transactional
    public Trade makeTrade(int offerId, int buyerId, double quantity) {
        Offer offer = offerDao.getOffersBy(new OfferFilter().restrictedToId(offerId))
                .stream()
                .findFirst()
                .orElseThrow(()->new NoSuchOfferException(offerId));

        if (offer.getSeller().getUsername().get().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
            throw new IllegalArgumentException("Cannot create trade on own offer");

        if (quantity > offer.getMaxInCrypto()*offer.getUnitPrice() || quantity < offer.getMinInCrypto()*offer.getUnitPrice())
            throw new IllegalArgumentException("Trade quantity is out of bounds");

        Trade newTrade =  tradeDao.makeTrade(offerId, buyerId, quantity);
        String firstChat = newTrade.getOffer().getComments();
        if (firstChat != null && !firstChat.equals(""))
            chatService.sendMessage(newTrade.getOffer().getSeller().getId(), newTrade.getTradeId(), firstChat);
        messageSenderFacade.sendNewTradeNotification(newTrade);
        return newTrade;
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.isUserOwnerOfTrade(authentication.principal, #tradeId)")
    public Trade rejectTrade(int tradeId) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()-> new NoSuchTradeException(tradeId));
        if (!trade.getStatus().equals(TradeStatus.PENDING))
            throw new InvalidTradeStatusChange(tradeId,TradeStatus.REJECTED);
        return tradeDao.changeTradeStatus(tradeId, TradeStatus.REJECTED);
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.isUserOwnerOfTrade(authentication.principal, #tradeId)")
    public Trade sellTrade(int tradeId) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        if (!trade.getStatus().equals(TradeStatus.ACCEPTED))
            throw new InvalidTradeStatusChange(tradeId,TradeStatus.SOLD);
        tradeDao.changeTradeStatus(tradeId, TradeStatus.SOLD);
        Offer offer = trade.getOffer();
        double newMaxInCrypto = offer.getMaxInCrypto() - trade.getQuantity() / offer.getUnitPrice();
        if (newMaxInCrypto < offer.getMinInCrypto()) {
            offer.setMaxInCrypto(0);
            offer.setMinInCrypto(0);
            offer.setOfferStatus(OfferStatus.SOL);
        } else {
            offer.setMaxInCrypto(newMaxInCrypto);
        }
        offerDao.modifyOffer(offer);

        // If a trade is in a forbidden state after some crypto of the offer was sold, change its status to rejected
        // automatically
        tradeDao.rejectAllRemainingTrades(offer.getOfferId());
        return trade;
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.isUserOwnerOfTrade(authentication.principal, #tradeId)")
    public Trade acceptTrade(int tradeId) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()-> new NoSuchTradeException(tradeId));
        if (!trade.getStatus().equals(TradeStatus.PENDING))
            throw new InvalidTradeStatusChange(tradeId,TradeStatus.ACCEPTED);
        return tradeDao.changeTradeStatus(tradeId, TradeStatus.ACCEPTED);
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.isUserBuyerOfTrade(authentication.principal, #tradeId)")
    public void deleteTrade(int tradeId) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()-> new NoSuchTradeException(tradeId));
        if (!trade.getStatus().equals(TradeStatus.PENDING))
            throw new InvalidTradeStatusChange(tradeId,TradeStatus.DELETED);
        tradeDao.deleteTrade(tradeId);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or @customPreAuthorizer.isUserPartOfTrade(authentication.principal, #tradeId)")
    public Optional<Trade> getTradeById(int tradeId) {
        return tradeDao.getTradeById(tradeId);
    }

    @Override
    @PreAuthorize("#username == authentication.principal")
    public Collection<Trade> getMostRecentTradesAsSeller(String username, int quantity) {
        return tradeDao.getMostRecentTradesAsSeller(username, quantity);
    }


    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal")
    public Collection<Trade> getTradesAsSeller(String username, int page, int pageSize, Set<TradeStatus> status, int offerId) {
        return tradeDao.getTradesAsSeller(username, page, pageSize, status, offerId);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal")
    public long getTradesAsSellerCount(String username, Set<TradeStatus> status, int offerId) {
        return tradeDao.getTradesAsSellerCount(username, status, offerId);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal")
    public Collection<Trade> getTradesAsBuyer(String username, int page, int pageSize, Set<TradeStatus> status) {
        return tradeDao.getTradesAsBuyer(username, page, pageSize, status);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal")
    public long getTradesAsBuyerCount(String username, Set<TradeStatus> status) {
        return tradeDao.getTradesAsBuyerCount(username, status);
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.isUserPartOfTrade(authentication.principal, #tradeId)")
    public void rateCounterPartUserRegardingTrade(String username, int rating, int tradeId) {

        if (rating < 1 || rating > 10)
            throw new IllegalArgumentException("Rating is out of bounds.");

        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        if(!trade.getStatus().equals(TradeStatus.SOLD))
            throw new NoRateableTradeException(tradeId);

        String buyerUsername = trade.getBuyer().getUserAuth().getUsername();

        if (username.equals(buyerUsername))
            updateSellerRating(rating,tradeId);
        else
            updateBuyerRating(rating,tradeId);
    }

    private void updateSellerRating(int newTradeRating, int tradeId){
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        User seller = trade.getOffer().getSeller();
        trade.setSeller_rating(newTradeRating);
        if(!trade.isSellerRated()){
            userService.updateRatingBy(seller.getUsername().orElseThrow(()-> new IllegalStateException() ), newTradeRating);
            trade.markSellerAsRated();
        }else
            updateUserRating(seller.getRatingSum() ,newTradeRating,seller);

    }

    private void updateBuyerRating(int newTradeRating, int tradeId){
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        User buyer = trade.getBuyer();
        trade.setBuyer_rating(newTradeRating);
        if(!trade.isBuyerRated()){
            userService.updateRatingBy(buyer.getUsername().orElseThrow(()-> new IllegalStateException()), newTradeRating);
            trade.markBuyerAsRated();
        }else
            updateUserRating(trade.getBuyer().getRatingSum() , newTradeRating,buyer);

    }



    private void updateUserRating(int oldTradeRating,int newTradeRating,  User user){
        int oldUserRating = user.getRatingSum();
        user.setRatingSum(oldUserRating - oldTradeRating + newTradeRating);
    }


}
