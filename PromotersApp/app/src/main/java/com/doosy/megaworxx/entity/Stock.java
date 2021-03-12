package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stock {
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

    @SerializedName("quantity")
    @Expose()
    private int quantity;

    @SerializedName("campaignLocationId")
    @Expose()
    private String campaignLocationId;

    @SerializedName("campaignId")
    @Expose()
    private String campaignId;

    @SerializedName("stockItemId")
    @Expose()
    private String stockItemId;

    public Stock(String id, String dateCreated, String campaignDateId, String campaignPromoterId,
                 int quantity, String campaignLocationId, String campaignId, String stockItemId) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.campaignDateId = campaignDateId;
        this.campaignPromoterId = campaignPromoterId;
        this.quantity = quantity;
        this.campaignLocationId = campaignLocationId;
        this.campaignId = campaignId;
        this.stockItemId = stockItemId;
    }

    public Stock(String id, String dateCreated, StockItem stockItem, String campaignDateId,
                 String campaignPromoterId, int quantity, String campaignLocationId, String campaignId, String stockItemId) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.stockItem = stockItem;
        this.campaignDateId = campaignDateId;
        this.campaignPromoterId = campaignPromoterId;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
