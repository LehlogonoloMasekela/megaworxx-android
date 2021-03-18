package com.doosy.megaworxx.model;

public class CampaignFilterModel {
    private String promoterId;
    private String fromDate;
    private String toDate;

    public CampaignFilterModel(String promoterId, String fromDate, String toDate) {
        this.promoterId = promoterId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getPromoterId() {
        return promoterId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    @Override
    public String toString() {
        return "CampaignFilterModel{" +
                "promoterId='" + promoterId + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
    }
}
