package com.example.myhome;

public class Security {

  private String name;
  private Long phone;
  private String email;
  boolean isRegistered;
  String UID;

  public Security() {
  }

  public String getUID() {
    return UID;
  }

  public void setUID(String UID) {
    this.UID = UID;
  }

  public Security(String name, Long phone, String email, boolean isRegistered, String UID) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.isRegistered = isRegistered;
    this.UID = UID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getPhone() {
    return phone;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isRegistered() {
    return isRegistered;
  }

  public void setRegistered(boolean registered) {
    isRegistered = registered;
  }
}
