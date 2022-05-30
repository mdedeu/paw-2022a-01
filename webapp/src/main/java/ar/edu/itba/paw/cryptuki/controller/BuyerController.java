package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.cryptuki.form.ProfilePicForm;
import ar.edu.itba.paw.cryptuki.form.SoldTradeForm;
import ar.edu.itba.paw.cryptuki.form.StatusTradeForm;
import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    private final TradeService tradeService;
    private final OfferService offerService;

    private final CryptocurrencyService cryptocurrencyService;

    private final PaymentMethodService paymentMethodService;

    private final UserService userService;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public BuyerController(TradeService tradeService, OfferService offerService, UserService userService,CryptocurrencyService cryptocurrencyService,PaymentMethodService paymentMethodService)
    {
        this.tradeService = tradeService;
        this.offerService = offerService;
        this.userService = userService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.paymentMethodService = paymentMethodService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView buyer(Authentication authentication,
                              @RequestParam(value = "page") final Optional<Integer> page,
                              @RequestParam(value = "status", required = false) final Optional<String> status,
                              @ModelAttribute("ProfilePicForm") ProfilePicForm form){
        ModelAndView mav = new ModelAndView("buyer/buyerIndex");

        String username = authentication.getName();
        int pageNumber= page.orElse(0);

        TradeStatus askedStatus = TradeStatus.valueOf(status.orElse("PENDING"));

        int tradeCount;
        Collection<Trade> tradeList;
        tradeCount= tradeService.getBuyingTradesByUsernameCount(username,askedStatus);
        tradeList = tradeService.getBuyingTradesByUsername(authentication.getName(), pageNumber, PAGE_SIZE, askedStatus);
        User user = userService.getUserInformation(username).orElseThrow(()->new NoSuchUserException(username));


        int pages = (tradeCount+PAGE_SIZE-1)/PAGE_SIZE;

        mav.addObject("username",username);
        mav.addObject("user",user);
        mav.addObject("noBuyingTrades", tradeList.isEmpty());
        mav.addObject("tradeStatusList",TradeStatus.values());
        mav.addObject("tradeList",tradeList);
        mav.addObject("pages",pages);
        mav.addObject("activePage",pageNumber);
        return mav;
    }

    @RequestMapping(value = {"/market"}, method = RequestMethod.GET)
    public ModelAndView landing(@RequestParam(value = "page") final Optional<Integer> page,
                                @RequestParam(value = "location", required = false) final String location,
                                @RequestParam(value = "coin", required = false) final String coin,
                                @RequestParam(value = "pm", required = false) final String paymentMethod,
                                @RequestParam(value = "price", required = false) final Double price,
                                @RequestParam(value = "orderingDirection", required = false) final Optional<Integer> orderingDirection,
                                @RequestParam(value = "orderingCriterion", required = false) final Optional<Integer> orderingCriterion,
                                final Authentication authentication) {

        final ModelAndView mav = new ModelAndView("index");

        int pageNumber = page.orElse(0);
        OfferFilter filter = new OfferFilter()
            .byCryptoCode(coin)
                .byPaymentMethod(paymentMethod)
                .byMinPrice(price)
                .byMaxPrice(price)
                .withPageSize(PAGE_SIZE)
                .fromPage(pageNumber)
                .byLocation(location)
                .withOrderingCriterion(orderingCriterion.orElse(0))
                .withOrderingDirection(orderingDirection.orElse(0));;

        int offerCount = offerService.countMarketOffersBy(filter, authentication == null ? null : authentication.getName());
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;

        mav.addObject("offerList", offerService.getMarketOffersBy(filter, authentication == null ? null : authentication.getName()));
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());
        mav.addObject("offerCount", offerCount);

        if( null != authentication){
            mav.addObject("userEmail", userService.getUserInformation(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getEmail());
        }
        return mav;
    }



}