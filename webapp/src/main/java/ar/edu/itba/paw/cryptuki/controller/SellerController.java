package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.SoldTradeForm;
import ar.edu.itba.paw.cryptuki.form.StatusTradeForm;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.TradeStatus;
import ar.edu.itba.paw.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final TradeService tradeService;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public SellerController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView seller(Authentication authentication, @RequestParam(value = "page") final Optional<Integer> page, final @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm, @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm){
        ModelAndView mav = new ModelAndView("seller/sellerIndex");

        String username = authentication.getName();
        int pageNumber= page.orElse(0);

        int tradeCount= tradeService.getSellingTradesByUsernameCount(username, TradeStatus.ACCEPTED);
        Collection<Trade> tradeList = tradeService.getSellingTradesByUsername(authentication.getName(), pageNumber, PAGE_SIZE, TradeStatus.ACCEPTED);

        int pages = (tradeCount+PAGE_SIZE-1)/PAGE_SIZE;

        mav.addObject("noSellingTrades", tradeList.isEmpty());
        mav.addObject("tradeStatusList",TradeStatus.values());
        mav.addObject("tradeList",tradeList);
        mav.addObject("pages",pages);
        mav.addObject("activePage",pageNumber);
        return mav;
    }

}
