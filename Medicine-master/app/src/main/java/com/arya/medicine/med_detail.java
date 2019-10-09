package com.arya.medicine;

import java.io.Serializable;

public class med_detail implements Serializable {
    public  String user;
    public String detail;
    public String manData;
    public String expData;
    public String bNo;
    public String quantity;
    public String medUrl;
    public String billUrl;
    public String phone;

    public med_detail() {
    }

    public med_detail(String user, String detail,String phone, String manData, String expData, String bNo, String quantity, String medUrl, String billUrl) {
        this.user = user;
        this.detail = detail;
        this.phone=phone;
        this.manData = manData;
        this.expData = expData;
        this.bNo = bNo;
        this.quantity = quantity;
        this.medUrl = medUrl;
        this.billUrl = billUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) { this.phone = phone;    }

    public String getManData() {
        return manData;
    }

    public void setManData(String manData) {
        this.manData = manData;
    }

    public String getExpData() {
        return expData;
    }

    public void setExpData(String expData) {
        this.expData = expData;
    }

    public String getbNo() {
        return bNo;
    }

    public void setbNo(String bNo) {
        this.bNo = bNo;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMedUrl() {
        return medUrl;
    }

    public void setMedUrl(String medUrl) {
        this.medUrl = medUrl;
    }

    public String getBillUrl() {
        return billUrl;
    }

    public void setBillUrl(String billUrl) {
        this.billUrl = billUrl;
    }
}
