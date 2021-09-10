package com.example.schoool_app;

import java.util.List;

public class data_complete {
    String address,admOpen,board,city,contact,establishmentYear,fb,getEstablishmentYear,fees,
            gradesfrom,gradesupto,insta,land,linkedin,name,schooltype,state,strength,website,youtube;
    List<String> chips_documents,chips_facility,photos;
    float total_ratings;
    float show_ratings;
    int count_ratings;
    List<String> reviews;
    public data_complete()
    {

    }

    public data_complete(String address, String admOpen, String board, String city, String contact, String establishmentYear, String fb, String getEstablishmentYear, String fees, String gradesfrom, String gradesupto, String insta, String land, String linkedin, String name, String schooltype, String state, String strength, String website, String youtube, List<String> chips_documents, List<String> chips_facility, List<String> photos, float total_ratings, float show_ratings, int count_ratings, List<String> reviews) {
        this.address = address;
        this.admOpen = admOpen;
        this.board = board;
        this.city = city;
        this.contact = contact;
        this.establishmentYear = establishmentYear;
        this.fb = fb;
        this.getEstablishmentYear = getEstablishmentYear;
        this.fees = fees;
        this.gradesfrom = gradesfrom;
        this.gradesupto = gradesupto;
        this.insta = insta;
        this.land = land;
        this.linkedin = linkedin;
        this.name = name;
        this.schooltype = schooltype;
        this.state = state;
        this.strength = strength;
        this.website = website;
        this.youtube = youtube;
        this.chips_documents = chips_documents;
        this.chips_facility = chips_facility;
        this.photos = photos;
        this.total_ratings = total_ratings;
        this.show_ratings = show_ratings;
        this.count_ratings = count_ratings;
        this.reviews = reviews;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdmOpen() {
        return admOpen;
    }

    public void setAdmOpen(String admOpen) {
        this.admOpen = admOpen;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEstablishmentYear() {
        return establishmentYear;
    }

    public void setEstablishmentYear(String establishmentYear) {
        this.establishmentYear = establishmentYear;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getGetEstablishmentYear() {
        return getEstablishmentYear;
    }

    public void setGetEstablishmentYear(String getEstablishmentYear) {
        this.getEstablishmentYear = getEstablishmentYear;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getGradesfrom() {
        return gradesfrom;
    }

    public void setGradesfrom(String gradesfrom) {
        this.gradesfrom = gradesfrom;
    }

    public String getGradesupto() {
        return gradesupto;
    }

    public void setGradesupto(String gradesupto) {
        this.gradesupto = gradesupto;
    }

    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchooltype() {
        return schooltype;
    }

    public void setSchooltype(String schooltype) {
        this.schooltype = schooltype;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public List<String> getChips_documents() {
        return chips_documents;
    }

    public void setChips_documents(List<String> chips_documents) {
        this.chips_documents = chips_documents;
    }

    public List<String> getChips_facility() {
        return chips_facility;
    }

    public void setChips_facility(List<String> chips_facility) {
        this.chips_facility = chips_facility;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public float getTotal_ratings() {
        return total_ratings;
    }

    public void setTotal_ratings(float total_ratings) {
        this.total_ratings = total_ratings;
    }

    public float getShow_ratings() {
        return show_ratings;
    }

    public void setShow_ratings(float show_ratings) {
        this.show_ratings = show_ratings;
    }

    public int getCount_ratings() {
        return count_ratings;
    }

    public void setCount_ratings(int count_ratings) {
        this.count_ratings = count_ratings;
    }

    public List<String> getReviews() {
        return reviews;
    }

    public void setReviews(List<String> reviews) {
        this.reviews = reviews;
    }
}


