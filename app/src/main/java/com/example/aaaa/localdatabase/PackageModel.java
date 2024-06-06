package com.example.aaaa.localdatabase;

public class PackageModel {
    String name, price, dailypay, limit;

    public PackageModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDailypay() {
        return dailypay;
    }

    public void setDailypay(String dailypay) {
        this.dailypay = dailypay;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public PackageModel(String name, String price, String dailypay, String limit) {
        this.name = name;
        this.price = price;
        this.dailypay = dailypay;
        this.limit = limit;
    }
}
