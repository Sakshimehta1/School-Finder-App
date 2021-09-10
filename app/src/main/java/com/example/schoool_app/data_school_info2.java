package com.example.schoool_app;

public class data_school_info2 {
    String admOpen,gradesfrom,gradesupto,Strength,board,establishmentYear, schooltype;

    public data_school_info2()
    {

    }

    public data_school_info2(String admOpen, String gradesfrom, String gradesupto, String strength, String board, String establishmentYear, String schooltype) {
        this.admOpen = admOpen;
        this.gradesfrom = gradesfrom;
        this.gradesupto = gradesupto;
        Strength = strength;
        this.board = board;
        this.establishmentYear = establishmentYear;
        this.schooltype = schooltype;
    }

    public String getAdmOpen() {
        return admOpen;
    }

    public void setAdmOpen(String admOpen) {
        this.admOpen = admOpen;
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

    public String getStrength() {
        return Strength;
    }

    public void setStrength(String strength) {
        Strength = strength;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getEstablishmentYear() {
        return establishmentYear;
    }

    public void setEstablishmentYear(String establishmentYear) {
        this.establishmentYear = establishmentYear;
    }

    public String getSchooltype() {
        return schooltype;
    }

    public void setSchooltype(String schooltype) {
        this.schooltype = schooltype;
    }
}
