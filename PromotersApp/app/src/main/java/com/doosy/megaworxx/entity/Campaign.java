package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Campaign implements Serializable {
    @SerializedName("id")
    @Expose()
    private String id;

    @SerializedName("name")
    @Expose()
    private String name;

    @SerializedName("clientId")
    @Expose()
    private String clientId;

    @SerializedName("campaignManagerId")
    @Expose()
    private String campaignManagerId;

    @SerializedName("startDate")
    @Expose()
    private String startDate;

    @SerializedName("endDate")
    @Expose()
    private String endDate;

    @SerializedName("startTime")
    @Expose()
    private String startTime;

    @SerializedName("endTime")
    @Expose()
    private String endTime;

    @SerializedName("dateCreated")
    @Expose()
    private String dateCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCampaignManagerId() {
        return campaignManagerId;
    }

    public void setCampaignManagerId(String campaignManagerId) {
        this.campaignManagerId = campaignManagerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", clientId='" + clientId + '\'' +
                ", campaignManagerId='" + campaignManagerId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }
}
