package ar.edu.itba.paw.service;
import ar.edu.itba.paw.model.LocationCountWrapper;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.OfferFilter;
import ar.edu.itba.paw.model.OfferStatus;
import ar.edu.itba.paw.model.parameterObject.OfferPO;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface OfferService {

    /**
     * Offer modification and creation. Output is not optional, throws exception on error.
     */
    Offer makeOffer(OfferPO offerPO);
    Offer modifyOffer(OfferPO offer);

    /**
     * Returns collection and count of offers that can be effectively bought by a user
     * Depends on the underlying implementation, but by and large it retrieves all approved offers
     * except those where the logged-in username is the owner
     */
    Collection<Offer> getOffers(OfferFilter filter);
    long countOffers(OfferFilter filter);


    /** Get offer by id. The first method is free to use, whereas the second method requires the caller to certify ownership */
    Optional<Offer> getOfferById(int id);
    Optional<Offer> getOfferIfAuthorized(int offerId);


    /** Get offers and count by username. */
    long countOffersByUsername(String username, Set<OfferStatus> offerStatus);
    Collection<Offer> getOffersByUsername(String username, int page, int pageSize, Set<OfferStatus> offerStatus);


    /** Modify an offer's state. Must have the appropriate role for calling these methods.*/
    void deleteOffer(int offerId);
    void sellerPauseOffer(int offerId);
    void resumeOffer(int offerId);
    void adminPauseOffer(int offerId);

    /** Get an ordered collection of [Location, Integer] pairs with the quantity of offers per location */
    Collection<LocationCountWrapper> getOfferCountByLocation();
}
