package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.Min;


public class TradeForm {

    @Min(1)
    private double quantity;

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

}
