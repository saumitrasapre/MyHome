package com.example.myhome;

public class DeliveryDetails {
    private String nameOnPackage;
    private Long deliveryPhoneResident;
    private String flatNoForDelivery;
    private String Uid;
    private String deliveryCode;
    private String dateSelected;
    private String courierService;
    private Boolean isReceived;

    public  DeliveryDetails()
    {

    }



    public DeliveryDetails(String nameOnPackage, Long deliveryPhoneResident, String flatNoForDelivery, String uid, String deliveryCode, String dateSelected, String courierService, Boolean isReceived) {
        this.nameOnPackage = nameOnPackage;
        this.deliveryPhoneResident = deliveryPhoneResident;
        this.flatNoForDelivery = flatNoForDelivery;
        Uid = uid;
        this.deliveryCode = deliveryCode;
        this.dateSelected = dateSelected;
        this.courierService = courierService;
        this.isReceived = isReceived;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getNameOnPackage() {
        return nameOnPackage;
    }

    public void setNameOnPackage(String nameOnPackage) {
        this.nameOnPackage = nameOnPackage;
    }

    public Long getDeliveryPhoneResident() {
        return deliveryPhoneResident;
    }

    public void setDeliveryPhoneResident(Long deliveryPhoneResident) {
        this.deliveryPhoneResident = deliveryPhoneResident;
    }

    public String getFlatNoForDelivery() {
        return flatNoForDelivery;
    }

    public void setFlatNoForDelivery(String flatNoForDelivery) {
        this.flatNoForDelivery = flatNoForDelivery;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(String dateSelected) {
        this.dateSelected = dateSelected;
    }

    public String getCourierService() {
        return courierService;
    }

    public void setCourierService(String courierService) {
        this.courierService = courierService;
    }

    public Boolean getReceived() {
        return isReceived;
    }

    public void setReceived(Boolean received) {
        isReceived = received;
    }
}
