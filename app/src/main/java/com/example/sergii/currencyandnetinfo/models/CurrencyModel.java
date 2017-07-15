package com.example.sergii.currencyandnetinfo.models;

/**
 * Created by Sergii on 15.07.2017.
 */

public class CurrencyModel {

    private String ccy;
    private String base_ccy;
    private String buy;
    private String sale;

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getBase_ccy() {
        return base_ccy;
    }

    public void setBase_ccy(String base_ccy) {
        this.base_ccy = base_ccy;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }

    public String toString(){
        return "CCY: "
                + ccy
                + " BASE_CCY: " + base_ccy
                + " BUY: " + buy
                + " SALE: " + sale;
    }

    public String getStringToView(){
        return "Buy: " + buy + " Sale: " + sale;
    }
}
