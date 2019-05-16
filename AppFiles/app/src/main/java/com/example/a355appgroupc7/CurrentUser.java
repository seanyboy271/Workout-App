package com.example.a355appgroupc7;

import android.content.Intent;
import android.widget.Toast;

public class CurrentUser {
    private static final CurrentUser ourInstance = new CurrentUser();

    public static CurrentUser getInstance() {
        return ourInstance;
    }

    private String email;
    private String username;
    private String userId;
    private String isCoach;
    private String isPrivate;
    private String Bio;

    private CurrentUser() {

    }

    public void resetUser() {
        setUserId(null);
        setEmail(null);
        setUsername(null);
        setIsCoach(null);
        setIsPrivate(null);
        setBio(null);
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getIsCoach() {
        return isCoach;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public String getBio(){ return Bio; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setIsCoach(String isCoach) {
        this.isCoach = isCoach;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setBio(String Bio){ this.Bio = Bio; }


    }

