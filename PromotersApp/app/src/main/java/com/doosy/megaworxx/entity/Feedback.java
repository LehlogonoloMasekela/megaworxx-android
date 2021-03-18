package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feedback {
    @SerializedName("formId")
    @Expose()
    private String formId;

    @SerializedName("customerName")
    @Expose()
    private String customerName;

    @SerializedName("id")
    @Expose()
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
