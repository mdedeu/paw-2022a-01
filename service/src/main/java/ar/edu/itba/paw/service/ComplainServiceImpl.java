package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.ClosedComplainException;
import ar.edu.itba.paw.exception.NoSuchComplainException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.parameterObject.ComplainPO;
import ar.edu.itba.paw.model.parameterObject.SolveComplainPO;
import ar.edu.itba.paw.persistence.ComplainDao;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.persistence.UserAuthDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@Service
public class ComplainServiceImpl implements ComplainService{

    private final ComplainDao complainDao;
    private final UserAuthDao userAuthDao;
    private final OfferDao offerDao;


    private final MessageSenderFacade messageSenderFacade;

    @Autowired
    public ComplainServiceImpl(ComplainDao complainDao, MessageSenderFacade messageSenderFacade, UserAuthDao userAuthDao, OfferDao offerDao) {
        this.complainDao = complainDao;
        this.messageSenderFacade = messageSenderFacade;
        this.userAuthDao = userAuthDao;
        this.offerDao = offerDao;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('USER') or @customPreAuthorizer.isUserPartOfTrade(authentication.principal, #complain)")
    public Complain makeComplain(ComplainPO complain) {
        if (complain == null)
            throw new NullPointerException("Complain Parameter Object object cannot be null.");
        Complain createdComplain = complainDao.makeComplain(complain);
        messageSenderFacade.sendComplaintReceipt(createdComplain.getComplainer(), createdComplain.getTrade(), createdComplain.getComplainerComments().orElse("No comments"));

        return createdComplain;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Complain> getComplainsBy(ComplainFilter filter) {
        if (filter == null)
            throw new NullPointerException("Filter object cannot be null.");
        return complainDao.getComplainsBy(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public long countComplainsBy(ComplainFilter filter) {
        if (filter == null)
            throw new NullPointerException("Filter object cannot be null.");
        return complainDao.getComplainCount(filter);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or @customPreAuthorizer.canUserPeepComplain(authentication.principal, #id)")
    public Optional<Complain> getComplainById(int id) {
        if (id < 0)
            throw new IllegalArgumentException("Complain id can only be non negative.");
        return getComplainsBy(new ComplainFilter().restrictedToComplainId(id))
                .stream().findFirst();
    }


    @Override
    @Secured("ROLE_ADMIN")
    @Transactional
    public void closeComplain(SolveComplainPO solveComplainPO) {
        int complainId = solveComplainPO.getComplainId();
        String username = solveComplainPO.getModeratorUsername();
        String comments = solveComplainPO.getComments();
        Complain complain = getComplainById(complainId).orElseThrow(()-> new NoSuchComplainException(complainId));
        if(complain.getStatus().equals(ComplainStatus.CLOSED))
            throw new ClosedComplainException(complainId);
        if (ComplaintResolution.DISMISS.equals(ComplaintResolution.valueOf(solveComplainPO.getResolution()))) {
            closeComplainWithDismiss(complainId, username, comments);
        } else {
            closeComplainWithKickout(complainId, username, comments);
        }

    }

    @Override
    public void getSupportFor(String email, String description, Locale locale) {
        messageSenderFacade.sendAnonymousComplaintReceipt(email, description, locale);
    }

    private void closeComplainWithKickout(int complainId, String moderatorUsername, String comment) {

        Complain complain = complainDao.closeComplain(complainId, moderatorUsername, comment).orElseThrow(()->new NoSuchComplainException(complainId));

        complain.setResolution(ComplaintResolution.KICK);
        complainDao.modifyComplain(complain);

        User kickedOutUser;
        Trade trade = complain.getTrade();

        // Choose who is to be kicked out, depending on who complained in the first place
        if (complain.getComplainer().getId() == trade.getBuyer().getId()){
            kickedOutUser = trade.getOffer().getSeller();
        }else{
            kickedOutUser = trade.getBuyer();
        }


        offerDao.pauseOffersFromUser(kickedOutUser.getId());
        userAuthDao.kickoutUser(kickedOutUser.getId());

        messageSenderFacade.sendYouWereKickedOutBecause(kickedOutUser, comment);
        messageSenderFacade.sendComplainClosedWithKickout(complain.getComplainer(), comment);
    }


    private void closeComplainWithDismiss(int complainId, String moderatorUsername, String comment){

        Complain complain = complainDao.closeComplain(complainId, moderatorUsername, comment).orElseThrow(()->new NoSuchComplainException(complainId));

        complain.setResolution(ComplaintResolution.DISMISS);
        complainDao.modifyComplain(complain);

        messageSenderFacade.sendComplainClosedWithDismission(complain.getComplainer(), comment);
    }




}
