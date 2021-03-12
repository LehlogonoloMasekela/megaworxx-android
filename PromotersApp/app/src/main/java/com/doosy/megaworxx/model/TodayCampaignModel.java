package com.doosy.megaworxx.model;

public class TodayCampaignModel {
    private String promoterId;
    private String campaignDate;

    public TodayCampaignModel(String promoterId, String campaignDate) {
        this.promoterId = promoterId;
        this.campaignDate = campaignDate;
    }

    public String getPromoterId() {
        return promoterId;
    }

    public String getCampaignDate() {
        return campaignDate;
    }
}
