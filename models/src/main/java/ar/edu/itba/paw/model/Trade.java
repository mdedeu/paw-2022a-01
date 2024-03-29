package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

@Entity
@Table(name="trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trade_trade_id_seq")
    @SequenceGenerator(sequenceName = "trade_trade_id_seq", name = "trade_trade_id_seq", allocationSize = 1)
    @Column(name="trade_id")
    private int tradeId;
    @ManyToOne
    @JoinColumn(name="offer_id")
    private Offer offer;
    @OneToOne
    @JoinColumn(name="buyer_id")
    private User buyer;
    @Column(name="last_modified", insertable = false)
    private LocalDateTime lastModified;
    @Column(name="status",length = 10)
    @Enumerated(EnumType.STRING)
    private TradeStatus status = TradeStatus.PENDING;
    @Column(name="quantity")
    private double quantity;
    @Column(name="rated_buyer")
    private boolean isBuyerRated;
    @Column(name="rated_seller")
    private boolean isSellerRated;

    @Column(name="buyer_rating")
    private Integer buyer_rating;

    public Integer getBuyer_rating() {
        return buyer_rating;
    }

    public void setBuyer_rating(Integer buyer_rating) {
        this.buyer_rating = buyer_rating;
    }

    public Integer getSeller_rating() {
        return seller_rating;
    }

    public void setSeller_rating(Integer seller_rating) {
        this.seller_rating = seller_rating;
    }

    @Column(name="seller_rating")
    private Integer seller_rating;

    @Column(name="q_unseen_msg_buyer")
    private int qUnseenMessagesBuyer;
    @Column(name="q_unseen_msg_seller")
    private int qUnseenMessagesSeller;
    @OneToMany(mappedBy = "trade", fetch = FetchType.LAZY)
    @OrderBy("message_date")
    private Collection<Message> messageCollection = new LinkedList<>();

    public Trade() {
        // Just for Hibernate!
    }

    public Trade(Offer offer, User buyer, double quantity) {
        this.offer = offer;
        this.buyer = buyer;
        this.quantity = quantity;
    }

    public int getTradeId() {
        return tradeId;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public Offer getOffer() {
        return offer;
    }

    public User getBuyer() {
        return buyer;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public double getQuantity() {
        return quantity;
    }

    public boolean isBuyerRated() {
        return isBuyerRated;
    }

    public boolean isSellerRated() {
        return isSellerRated;
    }

    public int getqUnseenMessagesBuyer() {
        return qUnseenMessagesBuyer;
    }

    public int getqUnseenMessagesSeller() {
        return qUnseenMessagesSeller;
    }

    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public void markBuyerAsRated() {
        this.isBuyerRated = true;
    }

    public void markSellerAsRated() {
        this.isSellerRated = true;
    }

    public void setqUnseenMessagesBuyer(int qUnseenMessagesBuyer) {
        this.qUnseenMessagesBuyer = qUnseenMessagesBuyer;
    }

    public void setqUnseenMessagesSeller(int qUnseenMessagesSeller) {
        this.qUnseenMessagesSeller = qUnseenMessagesSeller;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }


}
