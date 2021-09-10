package com.example.schoool_app;

import java.util.List;

public class data_school_info3 {
    String fees;
    List<String> chips_documents;
    List<String> chips_facility;
    String fb,insta,youtube,linkedin;

    public data_school_info3()
    {

    }

    public data_school_info3(String fees, List<String> chips_documents, List<String> chips_facility, String fb, String insta, String youtube, String linkedin) {
        this.fees = fees;
        this.chips_documents = chips_documents;
        this.chips_facility = chips_facility;
        this.fb = fb;
        this.insta = insta;
        this.youtube = youtube;
        this.linkedin = linkedin;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
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

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
}
