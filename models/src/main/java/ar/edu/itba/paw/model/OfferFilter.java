package ar.edu.itba.paw.model;

import java.util.*;
import java.util.stream.Collectors;

public class OfferFilter {

    /* Attributes that discriminate offers */
    private final Collection<String> cryptoCodes = new HashSet<>();
    private final Collection<Integer> restrictedToIds = new HashSet<>();
    private final Collection<String> restrictedToUsernames = new HashSet<>();
    private final Collection<String> excludedUsernames = new HashSet<>();
    private final Collection<Location> locations = EnumSet.noneOf(Location.class);
    private final Collection<PaymentMethod> paymentMethods = EnumSet.noneOf(PaymentMethod.class);
    private Collection<OfferStatus> status = EnumSet.noneOf(OfferStatus.class);

    private Double isQuantityInRange;

    /* Attributes that determine paging */
    private int page = 0;
    private int pageSize = 10;

    /* Attributes that order offers*/
    private OfferOrderCriteria orderCriteria = OfferOrderCriteria.DATE;

    /****/

    public OfferFilter withPaymentMethod(String paymentMethod) {
        if (paymentMethod != null)
            paymentMethods.add(PaymentMethod.valueOf(paymentMethod));
        return this;
    }

    public OfferFilter withPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod != null)
            paymentMethods.add(paymentMethod);
        return this;
    }

    public OfferFilter withOfferStatus(OfferStatus offerStatus) {
        if (offerStatus != null)
            status.add(offerStatus);
        return this;
    }

    public OfferFilter withOfferStatus(String offerStatus) {
        if (offerStatus != null)
            status.add(OfferStatus.valueOf(offerStatus));
        return this;
    }

    public OfferFilter withOfferStatus(Collection<OfferStatus> offerStatus) {
        status.addAll(offerStatus);
        return this;
    }

    public OfferFilter withLocation(String location) {
        if (location != null)
            locations.add(Location.valueOf(location));
        return this;
    }

    public OfferFilter withLocations(Collection<String> locations) {
        if (locations != null)
            this.locations.addAll(locations.stream().map(l->Location.valueOf(l)).collect(Collectors.toList()));
        return this;
    }

    public OfferFilter withLocation(Location location) {
        if (location != null)
            locations.add(location);
        return this;
    }

    public OfferFilter withCryptoCode(String cryptoCode) {
        if (cryptoCode != null)
            cryptoCodes.add(cryptoCode);
        return this;
    }

    public OfferFilter withCryptoCodes(Collection<String> cryptoCodes) {
        if (cryptoCodes != null)
            this.cryptoCodes.addAll(cryptoCodes);
        return this;
    }

    public OfferFilter excludeUsername(String username) {
        if (username != null)
            excludedUsernames.add(username);
        return this;
    }

    public OfferFilter excludeUsernames(Collection<String> excludedUsernames) {
        if (excludedUsernames != null)
            this.excludedUsernames.addAll(excludedUsernames);
        return this;
    }

    public OfferFilter restrictedToUsername(String username) {
        if (username != null)
            restrictedToUsernames.add(username);
        return this;
    }

    public OfferFilter restrictedToUsernames(Collection<String> usernames) {
        if (usernames != null)
            restrictedToUsernames.addAll(usernames);
        return this;
    }

    public OfferFilter restrictedToId(Integer offerId) {
        if  (offerId != null)
            restrictedToIds.add(offerId);
        return this;
    }

    public OfferFilter withIsQuantityInRange(Double quantity) {
        if  (quantity != null)
            this.isQuantityInRange = quantity;
        return this;
    }

    public OfferFilter withPage(int page) {
        this.page = page;
        return this;
    }

    public OfferFilter withPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public OfferFilter orderingBy(OfferOrderCriteria orderCriterion) {
        if (orderCriteria != null)
            this.orderCriteria = orderCriterion;
        return this;
    }

    /****/

    public Collection<PaymentMethod> getPaymentMethods() {
        return Collections.unmodifiableCollection(paymentMethods);
    }

    public Collection<String> getCryptoCodes() {
        return Collections.unmodifiableCollection(cryptoCodes);
    }

    public Collection<Integer> getRestrictedToIds() {
        return Collections.unmodifiableCollection(restrictedToIds);
    }

    public Collection<String> getRestrictedToUsernames() {
        return Collections.unmodifiableCollection(restrictedToUsernames);
    }

    public Collection<String> getExcludedUsernames() {
        return Collections.unmodifiableCollection(excludedUsernames);
    }

    public Collection<Location> getLocations() {
        return Collections.unmodifiableCollection(locations);
    }

    public Collection<OfferStatus> getStatus() {
        return Collections.unmodifiableCollection(status);
    }

    public OptionalDouble getIsQuantityInRange() {
        return isQuantityInRange == null ? OptionalDouble.empty() : OptionalDouble.of(isQuantityInRange);
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public OfferOrderCriteria getOrderCriteria() {
        return orderCriteria;
    }
}
