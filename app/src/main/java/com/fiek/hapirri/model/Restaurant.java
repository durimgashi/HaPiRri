package com.fiek.hapirri.model;

import java.util.List;

public class Restaurant {
    private String restName;
    private String address;
    private Double lattitude;
    private Double longitude;
    private List<Item> menu;
    private String image;

    public Restaurant() {
    }

    public Restaurant(String restName, String address, Double lattitude, Double longitude, List<Item> menu, String image) {
        this.restName = restName;
        this.address = address;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.menu = menu;
        this.image = image;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLattitude() {
        return lattitude;
    }

    public void setLattitude(Double lattitude) {
        this.lattitude = lattitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Item> getMenu() {
        return menu;
    }

    public void setMenu(List<Item> menu) {
        this.menu = menu;
    }
}
