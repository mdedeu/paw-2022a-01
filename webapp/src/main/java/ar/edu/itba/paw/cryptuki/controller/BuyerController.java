package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.LandingForm;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.cryptuki.form.ProfilePicForm;
import ar.edu.itba.paw.exception.NoSuchUserException;
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
import java.util.EnumSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    private final TradeService tradeService;
    private final OfferService offerService;

    private final CryptocurrencyService cryptocurrencyService;

    private final UserService userService;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public BuyerController(TradeService tradeService, OfferService offerService, UserService userService,CryptocurrencyService cryptocurrencyService)
    {
        this.tradeService = tradeService;
        this.offerService = offerService;
        this.userService = userService;
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView buyer(Authentication authentication, @RequestParam(value = "page") final Optional<Integer> page, @RequestParam(value = "status", required = false) final Optional<String> status, @ModelAttribute("profilePicForm") ProfilePicForm form){
        ModelAndView mav = new ModelAndView("buyer/buyerIndex");

        String username = authentication.getName();
        int pageNumber = page.orElse(0);

        long tradeCount;
        Collection<Trade> tradeList;
        EnumSet<TradeStatus> set;
        if (status.isPresent()) {
            TradeStatus askedStatus = TradeStatus.valueOf(status.get());
            set = EnumSet.of(askedStatus);
        } else {
            set= EnumSet.allOf(TradeStatus.class);
        }
        tradeCount = tradeService.getTradesAsBuyerCount(username, set);
        tradeList = tradeService.getTradesAsBuyer(authentication.getName(), pageNumber, PAGE_SIZE, set);
        User user = userService.getUserByUsername(username).orElseThrow(() -> new NoSuchUserException(username));
        int pages = (int)(tradeCount + PAGE_SIZE - 1) / PAGE_SIZE;

        mav.addObject("username", username);
        mav.addObject("user", user);
        mav.addObject("noBuyingTrades", tradeList.isEmpty());
        mav.addObject("tradeStatusList", TradeStatus.values());
        mav.addObject("tradeList", tradeList);
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        return mav;
    }

    @RequestMapping(value = {"/market"}, method = RequestMethod.GET)
    public ModelAndView landing(@Valid @ModelAttribute("landingForm") LandingForm form, final BindingResult errors) {

        if (errors.hasErrors())
            return new ModelAndView("redirect:/buyer/market?error");

        final ModelAndView mav = new ModelAndView("index");

        OfferFilter filter = form.toOfferFilter().withPageSize(PAGE_SIZE);
        long offerCount = offerService.countBuyableOffers(filter);
        long pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;

        Collection<Offer> offer = offerService.getBuyableOffers(filter);
        Collection<Cryptocurrency> cryptocurrencies = cryptocurrencyService.getAllCryptocurrencies();
        Collection<LocationCountWrapper> locationCountWrappers = offerService.getOfferCountByLocation();

        mav.addObject("selectedCoins", form.getCoins());
        mav.addObject("offerList", offer);
        mav.addObject("pages", pages);
        mav.addObject("activePage", form.getPage());
        mav.addObject("cryptocurrencies", cryptocurrencies);
        mav.addObject("paymentMethods", Arrays.asList(PaymentMethod.values()));
        mav.addObject("offerCount", offerCount);
        mav.addObject("locations", Arrays.asList(Location.values()));
        mav.addObject("locationsWithOffers", locationCountWrappers);
        mav.addObject("coins" , form.getCoins());
        mav.addObject("page" , form.getPage());
        mav.addObject("orderCriteria", form.getOrderCriteria());
        mav.addObject("arsAmount", form.getArsAmount());
        return mav;
    }

}
