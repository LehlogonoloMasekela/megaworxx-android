package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StockSaleBase implements Serializable {
    @SerializedName("id")
    @Expose()
    private String id;

    @SerializedName("dateCreated")
    @Expose()
    private String dateCreated;

    @SerializedName("stockItem")
    @Expose()
    private StockItem stockItem;

    @SerializedName("campaignDateId")
    @Expose()
    private String campaignDateId;

    @SerializedName("campaignPromoterId")
    @Expose()
    private String campaignPromoterId;

    @SerializedName("campaignLocationId")
    @Expose()
    private String campaignLocationId;

    @SerializedName("campaignId")
    @Expose()
    private String campaignId;

    @SerializedName("stockItemId")
    @Expose()
    private String stockItemId;

    @SerializedName("quantity")
    @Expose()
    protected int quantity;

    @SerializedName("price")
    @Expose()
    protected double price;

    @SerializedName("deviceId")
    @Expose()
    protected String deviceId;

    public StockSaleBase(String id, String dateCreated, StockItem stockItem, String campaignDateId,
                         String campaignPromoterId, String campaignLocationId, String campaignId, String stockItemId) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.stockItem = stockItem;
        this.campaignDateId = campaignDateId;
        this.campaignPromoterId = campaignPromoterId;
        this.campaignLocationId = campaignLocationId;
        this.campaignId = campaignId;
        this.stockItemId = stockItemId;
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

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
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

    public String getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(String stockItemId) {
        this.stockItemId = stockItemId;
    }

    public int getQuantity() {
        return quantity == 0 ? 1 : quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "StockSaleBase{" +
                "id='" + id + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", stockItem=" + stockItem +
                ", campaignDateId='" + campaignDateId + '\'' +
                ", campaignPromoterId='" + campaignPromoterId + '\'' +
                ", campaignLocationId='" + campaignLocationId + '\'' +
                ", campaignId='" + campaignId + '\'' +
                ", stockItemId='" + stockItemId + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
