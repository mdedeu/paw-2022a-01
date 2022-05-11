package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.OfferFilter;

import java.util.Collection;
import java.util.Optional;

public interface OfferDao {
    int getOfferCount(OfferFilter filter);
    Collection<Offer> getOffersBy(OfferFilter filter);

    int makeOffer(OfferDigest digest);

    void modifyOffer(OfferDigest digest);
    void deleteOffer(int offerId);

    void hardPauseOffer(int offerId);
    void pauseOffer(int offerId);
    void resumeOffer(int offerId);

    Optional<String> getOwner(int offerId);

    void setMaxQuantity(int offerId, float newQuantity);
}
