package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.cryptuki.form.RatingForm;
import ar.edu.itba.paw.cryptuki.form.StatusTradeForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.RatingService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.xml.ws.Binding;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Controller
public class TradeFluxController {

    private final OfferService offerService;
    private final TradeService tradeService;
    private final RatingService ratingService;
    private final UserService us;
    private static final int PAGE_SIZE = 7;

    @Autowired
    public TradeFluxController(OfferService offerService, TradeService tradeService, RatingService ratingService, UserService us) {
        this.offerService = offerService;
        this.tradeService = tradeService;
        this.ratingService = ratingService;
        this.us = us;
    }

    @RequestMapping(value = "/buy/{offerId}", method = RequestMethod.GET)
    public ModelAndView buyOffer(@PathVariable("offerId") final int offerId, @ModelAttribute("offerBuyForm") final OfferBuyForm form, final Authentication authentication) {

        ModelAndView mav = new ModelAndView("buyOffer");
        Offer offer =  offerService.getOfferById(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        mav.addObject("offer", offer);
        mav.addObject("userEmail", offer.getSeller().getEmail());
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));

        return mav;
    }


    @RequestMapping(value="/trade", method = RequestMethod.GET)
    public ModelAndView executeTrade(@ModelAttribute("statusTradeForm") StatusTradeForm statusTradeForm, Integer tradeId , Authentication authentication){
        Trade trade = tradeService.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        Offer offer = trade.getOffer();
        boolean buying = !offer.getSeller().getUsername().get().equals(authentication.getName());

        ModelAndView mav = new ModelAndView("trade");
        mav.addObject("buying",buying);
        mav.addObject("tradeId",trade.getTradeId());
        mav.addObject("trade",trade);
        mav.addObject("offer", offer);
        mav.addObject("amount", trade.getQuantity());
        mav.addObject("otherLastLogin",buying? LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()):LastConnectionUtils.toRelativeTime(trade.getUser().getLastLogin()));
        mav.addObject("status",trade.getStatus().toString());

        return mav;
    }


    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ModelAndView executeTradePost(@Valid @ModelAttribute("offerBuyForm") final OfferBuyForm offerBuyForm, final BindingResult errors, final Authentication authentication){

        if(errors.hasErrors()) {
            return buyOffer(offerBuyForm.getOfferId(), offerBuyForm, authentication);
        }

        int tradeId = tradeService.makeTrade(offerBuyForm.toTradeBuilder(authentication.getName()));
        return new ModelAndView("redirect:/trade"+"?tradeId="+tradeId);
    }

    @RequestMapping(value = "/receipt/{tradeId}", method = RequestMethod.GET)
    public ModelAndView receipt(@PathVariable("tradeId") final int tradeId, final Authentication authentication) {
        return receiptView("receipt", tradeId, authentication);
    }

    @RequestMapping(value = "/receiptDescription/{tradeId}", method = RequestMethod.GET)
    public ModelAndView receiptDescription(@ModelAttribute("ratingForm") RatingForm ratingForm, @PathVariable("tradeId") final int tradeId, final Authentication authentication){
        ModelAndView mav = receiptView("receiptDescription", tradeId, authentication);
        mav.addObject("rated", false);
        return mav;
    }

    @RequestMapping(value = "/receiptDescription/{tradeId}/success", method = RequestMethod.GET)
    public ModelAndView receiptDescriptionSuccess(@ModelAttribute("ratingForm") RatingForm ratingForm, @PathVariable("tradeId") final int tradeId, final Authentication authentication){
        ModelAndView mav = receiptView("receiptDescription", tradeId, authentication);
        mav.addObject("rated", true);
        return mav;
    }


    private ModelAndView receiptView(String viewName, int tradeId, Authentication authentication) {
        ModelAndView mav = new ModelAndView(viewName);

        Trade trade = tradeService.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        Offer offer = offerService.getOfferById(trade.getOfferId()).orElseThrow(()->new NoSuchOfferException(trade.getOfferId()));
        User user = us.getUserInformation(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName()));
        boolean buying = !offer.getSeller().getUsername().get().equals(authentication.getName());

        mav.addObject("user", user);
        mav.addObject("trade", trade);
        mav.addObject("offer", offer);
        mav.addObject("buying",buying);
        mav.addObject("otherLastLogin",buying ? LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()) : LastConnectionUtils.toRelativeTime(trade.getUser().getLastLogin()));
        mav.addObject("ratedBySeller", trade.getRatedSeller());
        mav.addObject("ratedByBuyer", trade.getRatedBuyer());
        return mav;
    }


    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public ModelAndView rate(@Valid @ModelAttribute("ratingForm") RatingForm ratingForm, final  BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return receiptDescription(ratingForm, ratingForm.getTradeId(), authentication);
        }

        ratingService.rate(ratingForm.getTradeId(), authentication.getName(),  ratingForm.getRating());
        return new ModelAndView("redirect:/receiptDescription/"+ratingForm.getTradeId()+"/success");
    }

    @RequestMapping(value = "/mytrades", method = RequestMethod.GET)
    public ModelAndView getMyTrades(Authentication authentication,@RequestParam(value = "page") final Optional<Integer> page){
        ModelAndView mav = new ModelAndView("tradePage");
        String username = authentication.getName();
        int pageNumber= page.orElse(0);
        int tradeCount = tradeService.getTradesByUsernameCount(username);
        int pages=(tradeCount+PAGE_SIZE-1)/PAGE_SIZE;
        Collection<Trade> tradeList = tradeService.getTradesByUsername(authentication.getName(),pageNumber,PAGE_SIZE);
        mav.addObject("tradeList",tradeList);
        mav.addObject("pages",pages);
        mav.addObject("activePage",pageNumber);
        return mav;
    }


    @RequestMapping(value="/changeStatus",method = RequestMethod.POST)
    public ModelAndView updateStatus(@Valid @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm, final BindingResult errors ,final Authentication authentication){
        if (! errors.hasErrors())
            tradeService.updateStatus(statusTradeForm.getTradeId(), TradeStatus.valueOf(statusTradeForm.getNewStatus()));

        return executeTrade(new StatusTradeForm(),statusTradeForm.getTradeId(),authentication);
    }




}
