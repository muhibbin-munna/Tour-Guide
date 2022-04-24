package com.tour.guide.tourguide.model;

import java.util.List;

public class CountryInfoModel {
    private String name;
    private String currency;
    private String description;
    private String type;
//    private List<String> languages;
//    private String capitalCity;
//    private List<String> cities;


    public CountryInfoModel(String name, String currency, String description, String type) {
        this.name = name;
        this.currency = currency;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
