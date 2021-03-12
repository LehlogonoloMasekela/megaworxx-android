package com.doosy.megaworxx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaleModel {

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

    @SerializedName("coordinates")
    @Expose()
    private String coordinates;

    @SerializedName("stockItemId")
    @Expose()
    private String stockItemId;

    @SerializedName("deviceId")
    @Expose()
    private String deviceId;

    @SerializedName("price")
    @Expose()
    private double price;

    @SerializedName("date")
    @Expose()
    private String date;


    public SaleModel(String campaignPromoterId, String campaignDateId, String campaignId,
                     String campaignLocationId, String coordinates, String stockItemId, String deviceId, double price) {
        this.campaignPromoterId = campaignPromoterId;
        this.campaignDateId = campaignDateId;
        this.campaignId = campaignId;
        this.campaignLocationId = campaignLocationId;
        this.coordinates = coordinates;
        this.stockItemId = stockItemId;
        this.deviceId = deviceId;
        this.price = price;
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

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(String stockItemId) {
        this.stockItemId = stockItemId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
