package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sales {
    @SerializedName("id")
    @Expose()
    private String id;

    @SerializedName("dateCreated")
    @Expose()
    private String dateCreated;

    @SerializedName("price")
    @Expose()
    private int price;

    @SerializedName("deviceId")
    @Expose()
    private String deviceId;

    @SerializedName("stockItem")
    @Expose()
    private StockItem stockItem;

    @SerializedName("stockItemId")
    @Expose()
    private String stockItemId;

    @SerializedName("coordinates")
    @Expose()
    private String coordinates;

    @SerializedName("campaignLocationId")
    @Expose()
    private String campaignLocationId;

    @SerializedName("campaignId")
    @Expose()
    private String campaignId;

    @SerializedName("campaignDateId")
    @Expose()
    private String campaignDateId;

    @SerializedName("campaignPromoterId")
    @Expose()
    private String campaignPromoterId;

    public Sales(String id, String dateCreated, int price, String deviceId, String stockItemId,
                 String coordinates, String campaignLocationId, String campaignId, String campaignDateId, String campaignPromoterId) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.price = price;
        this.deviceId = deviceId;
        this.stockItemId = stockItemId;
        this.coordinates = coordinates;
        this.campaignLocationId = campaignLocationId;
        this.campaignId = campaignId;
        this.campaignDateId = campaignDateId;
        this.campaignPromoterId = campaignPromoterId;
    }

    public Sales(String id, String dateCreated, int price, String deviceId, StockItem stockItem,
                 String stockItemId, String coordinates, String campaignLocationId, String campaignId, String campaignDateId, String campaignPromoterId) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.price = price;
        this.deviceId = deviceId;
        this.stockItem = stockItem;
        this.stockItemId = stockItemId;
        this.coordinates = coordinates;
        this.campaignLocationId = campaignLocationId;
        this.campaignId = campaignId;
        this.campaignDateId = campaignDateId;
        this.campaignPromoterId = campaignPromoterId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }

    public String getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(String stockItemId) {
        this.stockItemId = stockItemId;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getCampaignLocationId() {
        return campaignLocationId;
    }

    public void setCampaignLocationId(String campaignLocationId) {
        this.campaignLocationId = campaignLocationId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignDateId() {
        return campaignDateId;
    }

    public void setCampaignDateId(String campaignDateId) {
        this.campaignDateId = campaignDateId;
    }

    public String getCampaignPromoterId() {
        return campaignPromoterId;
    }

    public void setCampaignPromoterId(String campaignPromoterId) {
        this.campaignPromoterId = campaignPromoterId;
    }
}
