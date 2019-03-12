package com.mustika.weddingcardseller.weddingcardseller.model;

/**
 * Created by David on 23/02/2018.
 */

public class Seller {
    private int sellerId;
    private String username;
    private String email;
    private String telephone;
    private String city;
    private int buyerAmount;
    private String bannerUrl;

    public Seller(){
    }

    public Seller(int sellerId, String username, String email,
                  String telephone, String city, int buyerAmount, String bannerUrl){
        this.sellerId = sellerId;
        this.username = username;
        this.email = email;
        this.telephone = telephone;
        this.city = city;
        this.buyerAmount = buyerAmount;
        this.bannerUrl = bannerUrl;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getBuyerAmount() {
        return buyerAmount;
    }

    public void setBuyerAmount(int buyerAmount) {
        this.buyerAmount = buyerAmount;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
}
