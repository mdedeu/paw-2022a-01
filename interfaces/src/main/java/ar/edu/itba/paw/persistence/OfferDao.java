package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.OfferDigest;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface OfferDao {
    int getOfferCount(OfferFilter filter);
    Collection<Offer> getOffersBy(OfferFilter filter);

    void makeOffer(OfferDigest digest);

    void modifyOffer(OfferDigest digest);
    void deleteOffer(int offerId);

    void hardPauseOffer(int offerId);
    void pauseOffer(int offerId);
    void resumeOffer(int offerId);
}
