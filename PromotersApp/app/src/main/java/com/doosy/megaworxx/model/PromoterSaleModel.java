package com.doosy.megaworxx.model;

public class PromoterSaleModel {
    private String promoterId;
    private String campaignId ;
    private String campaignLocationId;

    public PromoterSaleModel(String promoterId, String campaignId, String campaignLocationId) {
        this.promoterId = promoterId;
        this.campaignId = campaignId;
        this.campaignLocationId = campaignLocationId;
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
}
