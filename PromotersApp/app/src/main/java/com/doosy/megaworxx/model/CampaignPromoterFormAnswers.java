package com.doosy.megaworxx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CampaignPromoterFormAnswers{

    @SerializedName("formQuestionId")
    @Expose()
    private String formQuestionId;

    @SerializedName("answer")
    @Expose()
    private String answer;

    public CampaignPromoterFormAnswers(String formQuestionId, String answer) {
        this.formQuestionId = formQuestionId;
        this.answer = answer;
    }

    public String getFormQuestionId() {
        return formQuestionId;
    }

    public void setFormQuestionId(String formQuestionId) {
        this.formQuestionId = formQuestionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}