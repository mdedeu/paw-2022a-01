package ar.edu.itba.paw;

import java.util.Date;

public class Offer {
    private Number offer_id;
    private int seller_id;
    private Date offer_date;
    private String coin_id; //fk to coin
    private double asking_price;
    private double coin_amount;

    public Offer(Number offer_id, int seller_id, Date offer_date, String coin_id, double asking_price, double coin_amount) {
        this.offer_id = offer_id;
        this.seller_id = seller_id;
        this.offer_date = offer_date;
        this.coin_id = coin_id;
        this.asking_price = asking_price;
        this.coin_amount = coin_amount;
    }

    public Number getId() {
        return offer_id;
    }

    public Date getOffer_date() {
        return offer_date;
    }

    public double getAsking_price() {
        return asking_price;
    }

    public double getCoin_amount() {
        return coin_amount;
    }

    public String getCoin_id() {
        return coin_id;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offer_id=" + offer_id +
                ", seller_id=" + seller_id +
                ", offer_date=" + offer_date +
                ", coin_id='" + coin_id + '\'' +
                ", asking_price=" + asking_price +
                ", coin_amount=" + coin_amount +
                '}';
    }
}
