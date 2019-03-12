package com.mustika.weddingcardseller.weddingcardseller.model;

/**
 * Created by apple on 3/14/18.
 */

public class Report {

    private int reportId;
    private int incomeTotal;
    private String month;
    private int monthBuyerAmount;
    private int monthIncome;
    private int monthSalary;
    private int sellerIdFk;

    public Report(){
    }

    public Report(int reportId, int incomeTotal, String month, int monthBuyerAmount, int monthIncome,
                  int monthSalary){
        this.reportId = reportId;
        this.incomeTotal = incomeTotal;
        this.month = month;
        this.monthBuyerAmount = monthBuyerAmount;
        this.monthIncome = monthIncome;
        this.monthSalary = monthSalary;
    }

    public Report(int reportId, int incomeTotal, String month, int monthBuyerAmount, int monthIncome,
                  int monthSalary, int sellerIdFk){
        this.reportId = reportId;
        this.incomeTotal = incomeTotal;
        this.month = month;
        this.monthBuyerAmount = monthBuyerAmount;
        this.monthIncome = monthIncome;
        this.monthSalary = monthSalary;
        this.sellerIdFk = sellerIdFk;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getIncomeTotal() {
        return incomeTotal;
    }

    public void setIncomeTotal(int incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getMonthBuyerAmount() {
        return monthBuyerAmount;
    }

    public void setMonthBuyerAmount(int monthBuyerAmount) {
        this.monthBuyerAmount = monthBuyerAmount;
    }

    public int getMonthIncome() {
        return monthIncome;
    }

    public void setMonthIncome(int monthIncome) {
        this.monthIncome = monthIncome;
    }

    public int getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(int monthSalary) {
        this.monthSalary = monthSalary;
    }

    public int getSellerIdFk() {
        return sellerIdFk;
    }

    public void setSellerIdFk(int sellerIdFk) {
        this.sellerIdFk = sellerIdFk;
    }
}
