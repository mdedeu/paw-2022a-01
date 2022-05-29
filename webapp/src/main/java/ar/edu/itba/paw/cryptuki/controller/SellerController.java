package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.ProfilePicForm;
import ar.edu.itba.paw.cryptuki.form.SoldTradeForm;
import ar.edu.itba.paw.cryptuki.form.StatusTradeForm;
import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.TradeStatus;
import ar.edu.itba.paw.persistence.User;
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
@RequestMapping("/seller")
public class SellerController {
    private final TradeService tradeService;
    private final OfferService offerService;

    private final CryptocurrencyService cryptocurrencyService;

    private final PaymentMethodService paymentMethodService;

    private final UserService userService;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public SellerController(TradeService tradeService, OfferService offerService, UserService userService,CryptocurrencyService cryptocurrencyService,PaymentMethodService paymentMethodService)
    {
        this.tradeService = tradeService;
        this.offerService = offerService;
        this.userService = userService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.paymentMethodService = paymentMethodService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView seller(Authentication authentication, @RequestParam(value = "page") final Optional<Integer> page, final @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm, @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm,@ModelAttribute("ProfilePicForm") ProfilePicForm form){
        ModelAndView mav = new ModelAndView("seller/sellerIndex");

        String username = authentication.getName();
        int pageNumber= page.orElse(0);
        User user = userService.getUserInformation(username).orElseThrow(()->new NoSuchUserException(username));


        int tradeCount= tradeService.getSellingTradesByUsernameCount(username, TradeStatus.ACCEPTED);
        Collection<Trade> tradeList = tradeService.getSellingTradesByUsername(authentication.getName(), pageNumber, PAGE_SIZE, TradeStatus.ACCEPTED);

        int pages = (tradeCount+PAGE_SIZE-1)/PAGE_SIZE;

        mav.addObject("username",username);
        mav.addObject("user",user);
        mav.addObject("noSellingTrades", tradeList.isEmpty());
        mav.addObject("tradeStatusList",TradeStatus.values());
        mav.addObject("tradeList",tradeList);
        mav.addObject("pages",pages);
        mav.addObject("activePage",pageNumber);
        return mav;
    }


    @RequestMapping(value = "/myoffers", method = RequestMethod.GET)
    public ModelAndView myOffers(@RequestParam("page")final Optional<Integer> page, final Authentication authentication, final @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm, @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm){
        ModelAndView mav = new ModelAndView("myOffers");
        int pageNumber = page.orElse(0);
        int offerCount = offerService.countOffersByUsername(authentication.getName());
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;
        Collection<Offer> offers = offerService.getOffersByUsername(authentication.getName() , pageNumber, PAGE_SIZE);
        if(offers.isEmpty())
            mav.addObject("noOffers",true);

        mav.addObject("offerList",offers);
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("userEmail", userService.getUserInformation(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getEmail());
        return mav;
    }


    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadOffer(@ModelAttribute("uploadOfferForm") final UploadOfferForm form, final Authentication authentication){
        ModelAndView mav = new ModelAndView("uploadPage");
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());


        if (form.getPaymentMethods() != null){
            List<String> paymentCodesAlreadySelected = Arrays.asList(form.getPaymentMethods());
            mav.addObject("selectedPayments", paymentCodesAlreadySelected);
        }

        return mav;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadOffer(@Valid @ModelAttribute("uploadOfferForm") final UploadOfferForm form, final BindingResult errors, final Authentication authentication){
        if (errors.hasErrors())
            return uploadOffer(form, authentication);
        int id = userService.getUserInformation(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getId();
        int offerId = offerService.makeOffer(form.toOfferDigest(id));
        return new ModelAndView("redirect:/offer/"+offerId+"/creationsuccess");
    }

}
