package com.example.myhome;

public class Upload {

  private String vName;
  private Long vNumber;
  private String mName;
  private String mImageUrl;

  public String getvName() {
    return vName;
  }

  public void setvName(String vName) {
    this.vName = vName;
  }

  public Long getvNumber() {
    return vNumber;
  }

  public void setvNumber(Long vNumber) {
    this.vNumber = vNumber;
  }

  public Upload()
  {
    //Empty Constructor Needed
  }

  public Upload(String visName, Long visPhNo,String name, String imageUrl) {

    if(name.trim().equals(""))
    {
      name="No name";
    }
    vName=visName;
    vNumber=visPhNo;
    mName = name;
    mImageUrl = imageUrl;
  }

  public String getmName() {
    return mName;
  }

  public void setmName(String mName) {
    this.mName = mName;
  }

  public String getmImageUrl() {
    return mImageUrl;
  }

  public void setmImageUrl(String mImageUrl) {
    this.mImageUrl = mImageUrl;
  }
}
