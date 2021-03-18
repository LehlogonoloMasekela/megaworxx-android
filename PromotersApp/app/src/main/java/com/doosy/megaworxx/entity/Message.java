package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("id")
    @Expose()
    private String id;

    @SerializedName("messageTime")
    @Expose()
    private String date;

    @SerializedName("subject")
    @Expose()
    private String subject;

    @SerializedName("body")
    @Expose()
    private String message;

    @SerializedName("status")
    @Expose()
    private String status;

    public Message(String id, String date, String subject, String message, String status) {
        this.id = id;
        this.date = date;
        this.subject = subject;
        this.message = message;
        this.status = status;
    }

    public Message(String id, String date, String subject, String message) {
        this.id = id;
        this.date = date;
        this.subject = subject;
        this.message = message;
    }

    public Message(String date, String subject, String message) {
        this.date = date;
        this.subject = subject;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
