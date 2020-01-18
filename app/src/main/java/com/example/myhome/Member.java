package com.example.myhome;

public class Member {

  private String name;
  private Long phone;
  private int numMember;
  private String flatno;
  private String email;
  boolean isRegistered;
  private String UID;

  public Member() {
    isRegistered=false;
  }



  public Member(String name, Long phone, int numMember, String flatno, String email, boolean isRegistered, String UID) {
    this.name = name;
    this.phone = phone;
    this.numMember = numMember;
    this.flatno = flatno;
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
  public String getUID() {
    return UID;
  }

  public void setUID(String UID) {
    this.UID = UID;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }

  public int getNumMember() {
    return numMember;
  }

  public void setNumMember(int numMember) {
    this.numMember = numMember;
  }

  public String getFlatno() {
    return flatno;
  }

  public void setFlatno(String flatno) {
    this.flatno = flatno;
  }

  public boolean isRegistered() {
    return isRegistered;
  }

  public void setRegistered(boolean registered) {
    isRegistered = registered;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
