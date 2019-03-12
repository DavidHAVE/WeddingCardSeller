package com.mustika.weddingcardseller.weddingcardseller.model;

/**
 * Created by apple on 3/9/18.
 */

public class Invoice {

    private String itemName, itemQuantity, itemRate, itemAmount;

    public Invoice(){
    }

    public Invoice(String itemName, String itemQuantity, String itemRate, String itemAmount){
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemRate = itemRate;
        this.itemAmount = itemAmount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemRate() {
        return itemRate;
    }

    public void setItemRate(String itemRate) {
        this.itemRate = itemRate;
    }

    public String getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(String itemAmount) {
        this.itemAmount = itemAmount;
    }
}
