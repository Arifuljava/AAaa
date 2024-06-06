package com.example.aaaa.localdatabase;

public class UserModel {
    String name, email, phonenumber,uuid, time, username,password,
            whatsappnumber, depositbalance,
            currentbalance, lastclick, refername,
            withdrawtotal, sendmoneytotal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWhatsappnumber() {
        return whatsappnumber;
    }

    public void setWhatsappnumber(String whatsappnumber) {
        this.whatsappnumber = whatsappnumber;
    }

    public String getDepositbalance() {
        return depositbalance;
    }

    public void setDepositbalance(String depositbalance) {
        this.depositbalance = depositbalance;
    }

    public String getCurrentbalance() {
        return currentbalance;
    }

    public void setCurrentbalance(String currentbalance) {
        this.currentbalance = currentbalance;
    }

    public String getLastclick() {
        return lastclick;
    }

    public void setLastclick(String lastclick) {
        this.lastclick = lastclick;
    }

    public String getRefername() {
        return refername;
    }

    public void setRefername(String refername) {
        this.refername = refername;
    }

    public String getWithdrawtotal() {
        return withdrawtotal;
    }

    public void setWithdrawtotal(String withdrawtotal) {
        this.withdrawtotal = withdrawtotal;
    }

    public String getSendmoneytotal() {
        return sendmoneytotal;
    }

    public void setSendmoneytotal(String sendmoneytotal) {
        this.sendmoneytotal = sendmoneytotal;
    }

    public UserModel(String name, String email, String phonenumber, String uuid, String time, String username, String password, String whatsappnumber, String depositbalance, String currentbalance,
                     String lastclick, String refername, String withdrawtotal, String sendmoneytotal) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.uuid = uuid;
        this.time = time;
        this.username = username;
        this.password = password;
        this.whatsappnumber = whatsappnumber;
        this.depositbalance = depositbalance;
        this.currentbalance = currentbalance;
        this.lastclick = lastclick;
        this.refername = refername;
        this.withdrawtotal = withdrawtotal;
        this.sendmoneytotal = sendmoneytotal;
    }

    public UserModel() {
    }
}
//How to call Registration
/*
 String preferencesName = "UserDetails";
        List<UserModel> moldList = Arrays.asList(
                new UserModel("name", "email", "phonenumber","uuid", "time", "username","password",
                        "whatsappnumber", "depositbalance",
                        "currentbalance", "lastclick", "refername",
                        "withdrawtotal", "sendmoneytota")
        );
        SharedPreferencesManager.saveListToSharedPreferences(this, preferencesName, moldList, "userdetailsKey");
 */