package com.example.a355appgroupc7;

public class Users {
    //instance vars
    private String email;
    private String username;
    private String password;
    private String hash;
    private String isCoach;
    private String isPrivate="1";
    private String bio="Hi";

    //Constructor
    Users(String email, String username,String password,String hash, String isCoach){
        this.email = email;
        this.username = username;
        this.password = password;
        this.hash = hash;
        this.isCoach = isCoach;
    }
    //Getters + Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getIsCoach() {
        return isCoach;
    }

    public void setIsCoach(String isCoach) {
        this.isCoach = isCoach;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
