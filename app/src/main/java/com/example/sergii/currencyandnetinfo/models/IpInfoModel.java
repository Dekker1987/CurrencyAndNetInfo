package com.example.sergii.currencyandnetinfo.models;

/**
 * Created by Sergii on 15.07.2017.
 */

public class IpInfoModel {

    private String IP;
    private String ISP;
    private String timeZone;
    private String country;
    private String countryCode;
    private String regionName;
    private String city;

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getISP() {
        return ISP;
    }

    public void setISP(String ISP) {
        this.ISP = ISP;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String toString(){
        return "IP: " + IP
                + " ISP: " + ISP
                + " TimeZone: " + timeZone
                + " Country:" + country
                + " Country Code: " + countryCode
                + " Region Name: " + regionName
                + " City: " + city;
    }
}
