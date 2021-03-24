package com.example.onlinebloodbank;

public class model {
    String  fullname,
            username,
            email,
            phone,
            bloodgrp,
            password,
            division,
            district,
            union,
            ctycorp,
    imageurl
            ;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    model(){



    }

    public model(String fullname, String username, String email, String phone, String bloodgrp, String password, String division, String district, String union, String ctycorp, String imageurl) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.bloodgrp = bloodgrp;
        this.password = password;
        this.division = division;
        this.district = district;
        this.union = union;
        this.ctycorp = ctycorp;
        this.imageurl = ctycorp;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodgrp() {
        return bloodgrp;
    }

    public void setBloodgrp(String bloodgrp) {
        this.bloodgrp = bloodgrp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getCtycorp() {
        return ctycorp;
    }

    public void setCtycorp(String ctycorp) {
        this.ctycorp = ctycorp;
    }



}
