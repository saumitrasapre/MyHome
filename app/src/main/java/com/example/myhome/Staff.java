package com.example.myhome;

public class Staff {

    private String designation;
    private String name;

    public Staff()
    {

    }


    public Staff(String designation, String name) {
        this.designation = designation;
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
