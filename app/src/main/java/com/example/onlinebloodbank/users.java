package com.example.onlinebloodbank;

public class users {


    public String  fullname,
            username,
            email,
            phone,
            bloodgrp,
            password,
            division,
            district,
            union,
            ctycorp
            ;
    public users(){


    }

    public users(String fullname, String username, String email, String phone, String bloodgrp, String password, String division, String district, String union, String ctycorp) {
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

    }
}
