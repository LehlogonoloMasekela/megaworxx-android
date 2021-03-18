package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Form {
    @SerializedName("id")
    @Expose()
    private String id;

    @SerializedName("name")
    @Expose()
    private String name;

    @SerializedName("formTypeId")
    @Expose()
    private String formTypeId;

    @SerializedName("formType")
    @Expose()
    private FormType formType;

    @SerializedName("formQuestions")
    @Expose()
    private List<FormQuestions> formQuestions;

    public Form(String formTypeId, FormType formType, List<FormQuestions> formQuestions) {
        this.formTypeId = formTypeId;
        this.formType = formType;
        this.formQuestions = formQuestions;
    }

    public Form(String id, String formTypeId, FormType formType, List<FormQuestions> formQuestions) {
        this.id = id;
        this.formTypeId = formTypeId;
        this.formType = formType;
        this.formQuestions = formQuestions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormTypeId() {
        return formTypeId;
    }

    public void setFormTypeId(String formTypeId) {
        this.formTypeId = formTypeId;
    }

    public FormType getFormType() {
        return formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public List<FormQuestions> getFormQuestions() {
        return formQuestions;
    }

    public void setFormQuestions(List<FormQuestions> formQuestions) {
        this.formQuestions = formQuestions;
    }
}
