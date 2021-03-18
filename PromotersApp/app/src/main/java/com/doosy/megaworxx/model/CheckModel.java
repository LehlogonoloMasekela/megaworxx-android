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

    @SerializedName("campaignLocationId")
    @Expose()
    private String campaignLocationId;

    @SerializedName("type")
    @Expose()
    private int type;

    public CheckModel(String campaignPromoterId, String campaignDateId, String campaignId, String campaignLocationId, int type) {
        this.campaignPromoterId = campaignPromoterId;
        this.campaignDateId = campaignDateId;
        this.campaignId = campaignId;
        this.campaignLocationId = campaignLocationId;
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

    public String getCampaignLocationId() {
        return campaignLocationId;
    }

    public void setCampaignLocationId(String campaignLocationId) {
        this.campaignLocationId = campaignLocationId;
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
                ", campaignLocationId='" + campaignLocationId + '\'' +
                ", type=" + type +
                '}';
    }
}
