package com.doosy.megaworxx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CheckModel implements Serializable {
    @SerializedName("campaignPromoterId")
    @Expose()
    private String campaignPromoterId;

    @SerializedName("campaignDateId")
    @Expose()
    private String campaignDateId;

    @SerializedName("campaignId")
    @Expose()
    private String campaignId;

    @SerializedName("coordinates")
    @Expose()
    private String coordinates;

    @SerializedName("type")
    @Expose()
    private int type;

    public CheckModel(String campaignPromoterId, String campaignDateId, String campaignId, String coordinates, int type) {
        this.campaignPromoterId = campaignPromoterId;
        this.campaignDateId = campaignDateId;
        this.campaignId = campaignId;
        this.coordinates = coordinates;
        this.type = type;
    }

    public String getCampaignPromoterId() {
        return campaignPromoterId;
    }

    public void setCampaignPromoterId(String campaignPromoterId) {
        this.campaignPromoterId = campaignPromoterId;
    }

    public String getCampaignDateId() {
        return campaignDateId;
    }

    public void setCampaignDateId(String campaignDateId) {
        this.campaignDateId = campaignDateId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CheckModel{" +
                "campaignPromoterId='" + campaignPromoterId + '\'' +
                ", campaignDateId='" + campaignDateId + '\'' +
                ", campaignId='" + campaignId + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
