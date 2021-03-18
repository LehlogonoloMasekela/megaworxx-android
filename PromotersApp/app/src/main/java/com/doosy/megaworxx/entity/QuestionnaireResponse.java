package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionnaireResponse {
    @SerializedName("customerName")
    @Expose()
    private String customerName;

    @SerializedName("trimmedFormAnswers")
    @Expose()
    private List<TrimmedFormAnswer> trimmedFormAnswers;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<TrimmedFormAnswer> getTrimmedFormAnswers() {
        return trimmedFormAnswers;
    }

    public void setTrimmedFormAnswers(List<TrimmedFormAnswer> trimmedFormAnswers) {
        this.trimmedFormAnswers = trimmedFormAnswers;
    }

    @Override
    public String toString() {
        return "QuestionnaireResponse{" +
                "customerName='" + customerName + '\'' +
                ", trimmedFormAnswers=" + trimmedFormAnswers +
                '}';
    }
}
