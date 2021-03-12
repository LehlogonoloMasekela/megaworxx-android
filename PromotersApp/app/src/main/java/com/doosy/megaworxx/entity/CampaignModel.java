package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CampaignModel implements Serializable {
    @SerializedName("id")
    @Expose()
    private String id;
    @SerializedName("campaignName")
    @Expose()
    private String campaignName;

    @SerializedName("location")
    @Expose()
    private String location;

    @SerializedName("campaignDateId")
    @Expose()
    private String campaignDateId;

    @SerializedName("promoterId")
    @Expose()
    private String promoterId;

    @SerializedName("campaignId")
    @Expose()
    private String campaignId;

    @SerializedName("campaignLocationId")
    @Expose()
    private String campaignLocationId;
    private String createdAt;

    public CampaignModel(String id, String campaignName, String createdAt) {
        this.id = id;
        this.campaignName = campaignName;
        this.createdAt = createdAt;
    }

    public CampaignModel(String id, String campaignName, String location, String campaignDateId,
                         String promoterId, String campaignId, String campaignLocationId, String createdAt) {
        this.id = id;
        this.campaignName = campaignName;
        this.location = location;
        this.campaignDateId = campaignDateId;
        this.promoterId = promoterId;
        this.campaignId = campaignId;
        this.campaignLocationId = campaignLocationId;
        this.createdAt = createdAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCampaignDateId() {
        return campaignDateId;
    }

    public void setCampaignDateId(String campaignDateId) {
        this.campaignDateId = campaignDateId;
    }

    public String getPromoterId() {
        return promoterId;
    }

    public void setPromoterId(String promoterId) {
        this.promoterId = promoterId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignLocationId() {
        return campaignLocationId;
    }

    public void setCampaignLocationId(String campaignLocationId) {
        this.campaignLocationId = campaignLocationId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Campaign{" +
                "id='" + id + '\'' +
                ", campaignName='" + campaignName + '\'' +
                ", location='" + location + '\'' +
                ", campaignDateId='" + campaignDateId + '\'' +
                ", promoterId='" + promoterId + '\'' +
                ", campaignId='" + campaignId + '\'' +
                ", campaignLocationId='" + campaignLocationId + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
