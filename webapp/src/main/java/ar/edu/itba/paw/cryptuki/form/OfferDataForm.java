package ar.edu.itba.paw.cryptuki.form;
import ar.edu.itba.paw.cryptuki.annotation.CollectionOfEnum;
import ar.edu.itba.paw.cryptuki.annotation.MinLessThanMax;
import ar.edu.itba.paw.cryptuki.annotation.ValueOfEnum;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.OfferStatus;
import ar.edu.itba.paw.model.TradeStatus;
import ar.edu.itba.paw.model.parameterObject.OfferPO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import java.util.Collection;

@MinLessThanMax(
        min="minInCrypto",
        max="maxInCrypto"
)
abstract public class OfferDataForm {

    private Integer sellerId;

    @CollectionOfEnum(enumClass = TradeStatus.class)
    private Collection<String> sts;

    public Collection<String> getSts() {
        return sts;
    }

    public void setSts(Collection<String> sts) {
        this.sts = sts;
    }

    @NotNull
    @DecimalMin("0.0000001")
    private Double minInCrypto;

    @NotNull
    @DecimalMin("0.0000001")
    private Double maxInCrypto;

    @NotNull
    @DecimalMin("10.0")
    private Double unitPrice;

    @Size(max = 140)
    private String firstChat;

    @Size(min = 1, max = 30)
    @NotNull
    @ValueOfEnum(enumClass = Location.class)
    private String location;


    public Double getMinInCrypto() {
        return minInCrypto;
    }
    public Double getMaxInCrypto() {
        return maxInCrypto;
    }
    public Double getUnitPrice() {
        return unitPrice;
    }
    public String getFirstChat() {
        return firstChat;
    }
    public String getLocation() {
        return location;
    }
    public Integer getSellerId() { return sellerId; }


    public void setMinInCrypto(Double minInCrypto) {
        this.minInCrypto = minInCrypto;
    }
    public void setMaxInCrypto(Double maxInCrypto) {
        this.maxInCrypto = maxInCrypto;
    }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public void setFirstChat(String firstChat) {
        this.firstChat = firstChat;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public OfferPO toOfferParameterObject() {
       return new OfferPO()
               .withLocation(Location.valueOf(location))
               .withSellerId(sellerId)
               .withMaxInCrypto(maxInCrypto)
               .withMinInCrypto(minInCrypto)
               .withFirstChat(firstChat)
               .withUnitPrice(unitPrice);
    }

    public void fillFromOffer(Offer offer) {
        setMinInCrypto(offer.getMinInCrypto());
        setMaxInCrypto(offer.getMaxInCrypto());
        setUnitPrice((offer.getUnitPrice()));
        setFirstChat(offer.getComments());
        setLocation(offer.getLocation().toString());
    }
}

