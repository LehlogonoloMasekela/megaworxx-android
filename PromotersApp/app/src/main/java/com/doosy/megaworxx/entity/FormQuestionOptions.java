package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormQuestionOptions {
    @SerializedName("id")
    @Expose()
    private String id;

    @SerializedName("name")
    @Expose()
    private String name;

    @SerializedName("formQuestionId")
    @Expose()
    private String formQuestionId;

    public FormQuestionOptions(String name, String formQuestionId) {
        this.name = name;
        this.formQuestionId = formQuestionId;
    }

    public FormQuestionOptions(String id, String name, String formQuestionId) {
        this.id = id;
        this.name = name;
        this.formQuestionId = formQuestionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormQuestionId() {
        return formQuestionId;
    }

    public void setFormQuestionId(String formQuestionId) {
        this.formQuestionId = formQuestionId;
    }
}
