package com.doosy.megaworxx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CampaignForm {

    @SerializedName("formId")
    @Expose()
    private String formId;

    @SerializedName("customerName")
    @Expose()
    private String customerName;

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

    @SerializedName("campaignPromoterFormAnswers")
    @Expose()
    private List<CampaignPromoterFormAnswers> campaignPromoterFormAnswers;


    public CampaignForm(String formId, String customerName, String campaignPromoterId,
                        String campaignDateId, String campaignId, String campaignLocationId,
                        String coordinates, List<CampaignPromoterFormAnswers> campaignPromoterFormAnswers) {
        this.formId = formId;
        this.customerName = customerName;
        this.campaignPromoterId = campaignPromoterId;
        this.campaignDateId = campaignDateId;
        this.campaignId = campaignId;
        this.campaignLocationId = campaignLocationId;
        this.coordinates = coordinates;
        this.campaignPromoterFormAnswers = campaignPromoterFormAnswers;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public List<CampaignPromoterFormAnswers> getCampaignPromoterFormAnswers() {
        return campaignPromoterFormAnswers;
    }

    public void setCampaignPromoterFormAnswers(List<CampaignPromoterFormAnswers> campaignPromoterFormAnswers) {
        this.campaignPromoterFormAnswers = campaignPromoterFormAnswers;
    }
}
