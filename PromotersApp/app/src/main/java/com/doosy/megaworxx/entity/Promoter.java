package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Promoter implements Serializable {
    @SerializedName("userId")
    @Expose()
    private String userId;

    @SerializedName("firstName")
    @Expose()
    private String firstName;

    @SerializedName("surname")
    @Expose()
    private String surname;

    @SerializedName("email")
    @Expose()
    private String email;

    @SerializedName("token")
    @Expose()
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
