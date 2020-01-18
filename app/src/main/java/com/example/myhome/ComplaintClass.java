package com.example.myhome;

import java.io.Serializable;

public class ComplaintClass implements Serializable {

    private String complaintCode;
    private String uid;
    private String residentName;
    private Long residentPhone;
    private String complaintCategory;
    private String complaintString;
    private Boolean isPending;


    public ComplaintClass()
    {

    }

    public ComplaintClass(String complaintCode, String uid, String residentName, Long residentPhone, String complaintCategory, String complaintString, Boolean isPending) {
        this.complaintCode = complaintCode;
        this.uid = uid;
        this.residentName = residentName;
        this.residentPhone = residentPhone;
        this.complaintCategory = complaintCategory;
        this.complaintString = complaintString;
        this.isPending = isPending;
    }

    public String getComplaintCode() {
        return complaintCode;
    }

    public void setComplaintCode(String complaintCode) {
        this.complaintCode = complaintCode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResidentName() {
        return residentName;
    }

    public void setResidentName(String residentName) {
        this.residentName = residentName;
    }

    public Long getResidentPhone() {
        return residentPhone;
    }

    public void setResidentPhone(Long residentPhone) {
        this.residentPhone = residentPhone;
    }

    public String getComplaintCategory() {
        return complaintCategory;
    }

    public void setComplaintCategory(String complaintCategory) {
        this.complaintCategory = complaintCategory;
    }

    public String getComplaintString() {
        return complaintString;
    }

    public void setComplaintString(String complaintString) {
        this.complaintString = complaintString;
    }

    public Boolean getPending() {
        return isPending;
    }

    public void setPending(Boolean pending) {
        isPending = pending;
    }
}

