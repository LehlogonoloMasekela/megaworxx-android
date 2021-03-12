package com.doosy.megaworxx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddStockModel {
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

    @SerializedName("quantity")
    @Expose()
    private int quantity;

    private String itemName;

    public AddStockModel(String campaignPromoterId, String campaignDateId, String campaignId,
                         String campaignLocationId, String coordinates, String stockItemId, String itemName) {
        this.campaignPromoterId = campaignPromoterId;
        this.campaignDateId = campaignDateId;
        this.campaignId = campaignId;
        this.campaignLocationId = campaignLocationId;
        this.coordinates = coordinates;
        this.stockItemId = stockItemId;
        this.quantity = 0;
        this.itemName = itemName;
    }

    public String getCampaignPromoterId() {
        return campaignPromoterId;
    }

    public String getCampaignDateId() {
        return campaignDateId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public String getCampaignLocationId() {
        return campaignLocationId;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getStockItemId() {
        return stockItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "AddStockModel{" +
                "campaignPromoterId='" + campaignPromoterId + '\'' +
                ", campaignDateId='" + campaignDateId + '\'' +
                ", campaignId='" + campaignId + '\'' +
                ", campaignLocationId='" + campaignLocationId + '\'' +
                ", coordinates='" + coordinates + '\'' +
                ", stockItemId='" + stockItemId + '\'' +
                ", quantity=" + quantity +
                ", itemName='" + itemName + '\'' +
                '}';
    }
}
