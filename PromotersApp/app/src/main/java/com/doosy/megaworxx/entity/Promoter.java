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

    @SerializedName("idNumber")
    @Expose()
    private String idNumber;


    @SerializedName("phone")
    @Expose()
    private String phone;

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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName(){
        return firstName +" "+ surname;
    }
}
