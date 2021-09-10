package com.example.schoool_app;

public class listdata {
    int image;
    String schoolName;
    String schoolLoc;
    int ratings;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolLoc() {
        return schoolLoc;
    }

    public void setSchoolLoc(String schoolLoc) {
        this.schoolLoc = schoolLoc;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public listdata(int image, String schoolName, String schoolLoc, int ratings) {
        this.image = image;
        this.schoolName = schoolName;
        this.schoolLoc = schoolLoc;
        this.ratings = ratings;
    }
}
