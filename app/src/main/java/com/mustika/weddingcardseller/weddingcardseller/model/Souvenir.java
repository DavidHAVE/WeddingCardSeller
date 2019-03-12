package com.mustika.weddingcardseller.weddingcardseller.model;

/**
 * Created by apple on 3/15/18.
 */

public class Souvenir {

    private int souvenirId;
    private String name;
    private int price;
    private String imageUrl;

    public Souvenir(){
    }

    public Souvenir(int souvenirId, String name, int price, String imageUrl){
        this.souvenirId = souvenirId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getSouvenirId() {
        return souvenirId;
    }

    public void setSouvenirId(int souvenirId) {
        this.souvenirId = souvenirId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
