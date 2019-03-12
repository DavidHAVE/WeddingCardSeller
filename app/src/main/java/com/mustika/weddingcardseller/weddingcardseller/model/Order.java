package com.mustika.weddingcardseller.weddingcardseller.model;

/**
 * Created by David on 23/02/2018.
 */

public class Order {

    private int orderId;
    private String fullNameM;
    private String nickNameM;
    private String fatherFullNameM;
    private String motherFullNameM;
    private String parentAddressM;
    private String fullNameW;
    private String nickNameW;
    private String fatherFullNameW;
    private String motherFullNameW;
    private String parentAddressW;
    private String dayAndDate;
    private String hour;
    private String place;
    private boolean paymentCompleted;
    private String orderTime;
    private int orderPrice;
    private String fullNameO;
    private String nickNameO;
    private String emailO;
    private String telephoneO;
    private String sellerName;
    private int discountIdFK;
    private int itemIdFK;
    private int souvenirIdFK;
    private int sellerIdFK;

    public Order(){
    }

    public Order(int orderId, String fullNameM, String nickNameM, String fatherFullName, String motherFullNameM, String parentAddressM, String fullNameW,
                 String nickNameW, String fatherFullNameW, String motherFullNameW, String parentAddressW, String dayAndDate, String hour, String place,
                 boolean paymentCompleted, String orderTime, int orderPrice, String fullNameO, String nickNameO, String emailO, String telephoneO, String sellerName,
                 int discountIdFK, int itemIdFK, int souvenirIdFK, int sellerIdFK){
        this.orderId = orderId;
        this.fullNameM = fullNameM;
        this.nickNameM = nickNameM;
        this.fatherFullNameM = fatherFullName;
        this.motherFullNameM = motherFullNameM;
        this.parentAddressM = parentAddressM;
        this.fullNameW = fullNameW;
        this.nickNameW = nickNameW;
        this.fatherFullNameW = fatherFullNameW;
        this.motherFullNameW = motherFullNameW;
        this.parentAddressW = parentAddressW;
        this.dayAndDate = dayAndDate;
        this.hour = hour;
        this.place = place;
        this.paymentCompleted = paymentCompleted;
        this.orderTime = orderTime;
        this.orderPrice = orderPrice;
        this.fullNameO = fullNameO;
        this.nickNameO = nickNameO;
        this.emailO = emailO;
        this.telephoneO = telephoneO;
        this.sellerName = sellerName;
        this.discountIdFK = discountIdFK;
        this.itemIdFK = itemIdFK;
        this.souvenirIdFK = souvenirIdFK;
        this.sellerIdFK = sellerIdFK;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getFullNameM() {
        return fullNameM;
    }

    public void setFullNameM(String fullNameM) {
        this.fullNameM = fullNameM;
    }

    public String getNickNameM() {
        return nickNameM;
    }

    public void setNickNameM(String nickNameM) {
        this.nickNameM = nickNameM;
    }

    public String getFatherFullNameM() {
        return fatherFullNameM;
    }

    public void setFatherFullNameM(String fatherFullNameM) {
        this.fatherFullNameM = fatherFullNameM;
    }

    public String getMotherFullNameM() {
        return motherFullNameM;
    }

    public void setMotherFullNameM(String motherFullNameM) {
        this.motherFullNameM = motherFullNameM;
    }

    public String getParentAddressM() {
        return parentAddressM;
    }

    public void setParentAddressM(String parentAddressM) {
        this.parentAddressM = parentAddressM;
    }

    public String getFullNameW() {
        return fullNameW;
    }

    public void setFullNameW(String fullNameW) {
        this.fullNameW = fullNameW;
    }

    public String getNickNameW() {
        return nickNameW;
    }

    public void setNickNameW(String nickNameW) {
        this.nickNameW = nickNameW;
    }

    public String getFatherFullNameW() {
        return fatherFullNameW;
    }

    public void setFatherFullNameW(String fatherFullNameW) {
        this.fatherFullNameW = fatherFullNameW;
    }

    public String getMotherFullNameW() {
        return motherFullNameW;
    }

    public void setMotherFullNameW(String motherFullNameW) {
        this.motherFullNameW = motherFullNameW;
    }

    public String getParentAddressW() {
        return parentAddressW;
    }

    public void setParentAddressW(String parentAddressW) {
        this.parentAddressW = parentAddressW;
    }

    public String getDayAndDate() {
        return dayAndDate;
    }

    public void setDayAndDate(String dayAndDate) {
        this.dayAndDate = dayAndDate;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getFullNameO() {
        return fullNameO;
    }

    public void setFullNameO(String fullNameO) {
        this.fullNameO = fullNameO;
    }

    public String getNickNameO() {
        return nickNameO;
    }

    public void setNickNameO(String nickNameO) {
        this.nickNameO = nickNameO;
    }

    public String getEmailO() {
        return emailO;
    }

    public void setEmailO(String emailO) {
        this.emailO = emailO;
    }

    public String getTelephoneO() {
        return telephoneO;
    }

    public void setTelephoneO(String telephoneO) {
        this.telephoneO = telephoneO;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public int getDiscountIdFK() {
        return discountIdFK;
    }

    public void setDiscountIdFK(int discountIdFK) {
        this.discountIdFK = discountIdFK;
    }

    public int getItemIdFK() {
        return itemIdFK;
    }

    public void setItemIdFK(int itemIdFK) {
        this.itemIdFK = itemIdFK;
    }

    public int getSouvenirIdFK() {
        return souvenirIdFK;
    }

    public void setSouvenirIdFK(int souvenirIdFK) {
        this.souvenirIdFK = souvenirIdFK;
    }

    public int getSellerIdFK() {
        return sellerIdFK;
    }

    public void setSellerIdFK(int sellerIdFK) {
        this.sellerIdFK = sellerIdFK;
    }
}
