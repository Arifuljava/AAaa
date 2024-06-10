package com.example.aaaa.localdatabase;

public class WorkModel {
    String name, date, ammount, status;

    public WorkModel(String name, String date, String ammount, String status) {
        this.name = name;
        this.date = date;
        this.ammount = ammount;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WorkModel() {
    }
}
