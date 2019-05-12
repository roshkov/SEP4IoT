package com.application.cmapp.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties

public class Admin {

    public String email;
    public String name;


    public Admin() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public Admin(String email,String name)
    {
        this.email = email;
        this.name = name;

    }
}
