package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FormQuestions {
    @SerializedName("id")
    @Expose()
    private String id;

    @SerializedName("formId")
    @Expose()
    private String formId;

    @SerializedName("formQuestionTypeId")
    @Expose()
    private String formQuestionTypeId;

    @SerializedName("question")
    @Expose()
    private String question;

    @SerializedName("formQuestionOptions")
    @Expose()
    private List<FormQuestionOptions> formQuestionOptions;

    private String answer;

    public FormQuestions(String formId, String formQuestionTypeId, String question,
                         List<FormQuestionOptions> formQuestionOptions) {
        this.formId = formId;
        this.formQuestionTypeId = formQuestionTypeId;
        this.question = question;
        this.formQuestionOptions = formQuestionOptions;
    }

    public String getId() {
        return id;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormQuestionTypeId() {
        return formQuestionTypeId;
    }

    public void setFormQuestionTypeId(String formQuestionTypeId) {
        this.formQuestionTypeId = formQuestionTypeId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<FormQuestionOptions> getFormQuestionOptions() {
        return formQuestionOptions;
    }

    public void setFormQuestionOptions(List<FormQuestionOptions> formQuestionOptions) {
        this.formQuestionOptions = formQuestionOptions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
