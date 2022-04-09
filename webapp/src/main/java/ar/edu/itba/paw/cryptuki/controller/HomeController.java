package ar.edu.itba.paw.cryptuki.controller;


import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller  /* Requests can be dispatched to this class */
public class HomeController {

    private final UserService us;
    private final OfferService offerService;

    @Autowired
    public HomeController(UserService us, OfferService offerService) {
        this.us = us;
        this.offerService = offerService;
    }

    @RequestMapping("/") /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView helloWorld() {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("views/index"); /* Load a jsp file */

        Iterable<Offer> offers = offerService.getAllOffers();
        System.out.println("OFFERS"+offers);
        mav.addObject("offerList",offers);
        String[] payments = {"bru", "mp"}; //this is WRONG, it should get the info from the offer. Demostrative purposes only
        mav.addObject("payments", payments);
        return mav;
    }

}
