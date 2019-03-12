package com.mustika.weddingcardseller.weddingcardseller.model;

/**
 * Created by apple on 3/15/18.
 */

public class AddOn {

    private int addonId;
    private String name;
    private int price;

    public AddOn(){
    }

    public AddOn(int addonId, String name, int price){
        this.addonId = addonId;
        this.name = name;
        this.price = price;
    }

    public int getAddonId() {
        return addonId;
    }

    public void setAddonId(int addonId) {
        this.addonId = addonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
