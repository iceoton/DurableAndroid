package com.iceoton.durable.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_key")
    private String userKey;
    @SerializedName("name")
    private String firstName;
    @SerializedName("lastname")
    private String lastName;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("user_photo")
    private String photo;
    @SerializedName("user_class")
    private int userClass;
    @SerializedName("email")
    private String email;
    @SerializedName("user_status")
    private int status;



    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getUserClass() {
        return userClass;
    }

    public void setUserClass(int userClass) {
        this.userClass = userClass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
