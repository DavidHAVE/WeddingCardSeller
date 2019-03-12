package com.mustika.weddingcardseller.weddingcardseller.model;

/**
 * Created by David on 23/02/2018.
 */

public class Discount {

    private int discountId;
    private int total;
    private int discount;

    public Discount(){
    }

    public Discount(int discountId, int total, int discount){
        this.discountId = discountId;
        this.total = total;
        this.discount = discount;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
