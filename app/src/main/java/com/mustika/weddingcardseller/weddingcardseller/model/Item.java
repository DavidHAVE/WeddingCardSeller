package com.mustika.weddingcardseller.weddingcardseller.model;

/**
 * Created by David on 23/02/2018.
 */

public class Item {

    private int itemId;
    private String name;
    private String description;
    private int price;
    private String imageUrl;

    public Item(){
    }

    public Item(int itemId, String name, String description,
                int price, String imageUrl){
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
