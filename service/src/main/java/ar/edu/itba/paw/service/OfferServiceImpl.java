package ar.edu.itba.paw.service;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.persistence.OfferFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferDao offerDao;
    private UserService userService;

    @Autowired
    public OfferServiceImpl(OfferDao offerDao, UserService userService) {
        this.offerDao = offerDao;
        this.userService = userService;
    }

    @Override
    public void makeOffer(OfferDigest digest) {
        offerDao.makeOffer(digest);
    }

    @Override
    public Optional<Offer> getOfferById(int id) {
        Collection<Offer> offer = offerDao.getOffersBy(new OfferFilter().byOfferId(id));
        return offer.isEmpty() ? Optional.empty() : Optional.of(offer.iterator().next());
    }

    @Override
    public Collection<Offer> getOfferBy(OfferFilter filter) {
        return offerDao.getOffersBy(filter);
    }

    @Override
    public int countOffersBy(OfferFilter filter) {
        return offerDao.getOfferCount(filter);
    }

    @Override
    @Transactional
    public void modifyOffer(OfferDigest digest) {
        offerDao.modifyOffer(digest);
    }

    @Override
    @Transactional
    public void deleteOffer(int offerId) {
        offerDao.deleteOffer(offerId);
    }

    @Override
    @Transactional
    public void pauseOffer(int offerId) {
        offerDao.pauseOffer(offerId);
    }

    @Override
    @Transactional
    public void hardPauseOffer(int offerId) {
        offerDao.hardPauseOffer(offerId);
    }

    @Override
    @Transactional
    public void resumeOffer(int offerId) {
        offerDao.resumeOffer(offerId);
    }


}
