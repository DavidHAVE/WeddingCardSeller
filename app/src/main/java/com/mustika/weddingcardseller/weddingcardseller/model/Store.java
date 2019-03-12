package com.mustika.weddingcardseller.weddingcardseller.model;

/**
 * Created by apple on 3/16/18.
 */

public class Store {

    private int storeId;
    private String storeName;
    private String storeDescription;
    private int storePrice;
    private int storeTotal;
    private int storeRate;
    private String storeImageUrl;

    public Store(){
    }

    public Store(String storeName, int storeTotal, int storePrice){
        this.storeName = storeName;
        this.storeTotal = storeTotal;
        this.storePrice = storePrice;
    }


    public Store(int storeId, String storeName, int storePrice, int storeTotal){
        this.storeId = storeId;
        this.storeName = storeName;
        this.storePrice = storePrice;
        this.storeTotal = storeTotal;
    }

    public Store(String storeName, int storeTotal, int storeRate, int storePrice){
        this.storeName = storeName;
        this.storeTotal = storeTotal;
        this.storeRate = storeRate;
        this.storePrice = storePrice;
    }

    public Store(int storeId, String storeName, String storeDescription, int storePrice, int storeTotal, String storeImageUrl){
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.storePrice = storePrice;
        this.storeTotal = storeTotal;
        this.storeImageUrl = storeImageUrl;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public int getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(int storePrice) {
        this.storePrice = storePrice;
    }

    public int getStoreTotal() {
        return storeTotal;
    }

    public void setStoreTotal(int storeTotal) {
        this.storeTotal = storeTotal;
    }

    public int getStoreRate() {
        return storeRate;
    }

    public void setStoreRate(int storeRate) {
        this.storeRate = storeRate;
    }

    public String getStoreImageUrl() {
        return storeImageUrl;
    }

    public void setStoreImageUrl(String storeImageUrl) {
        this.storeImageUrl = storeImageUrl;
    }
}
