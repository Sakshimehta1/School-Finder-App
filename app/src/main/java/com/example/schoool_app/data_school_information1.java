package com.example.schoool_app;

public class data_school_information1 {
    String name,address,city,state,land,website,contact;

    public data_school_information1()
    {

    }

    public data_school_information1(String name, String address, String city, String state, String land, String website, String contact) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.land = land;
        this.website = website;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
