package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Make extends BaseEntity implements Serializable {

    @SerializedName("models")
    @Expose()
    private List<Model> models;

    public Make(String id, String name, String dateCreated, String createdBy, List<Model> models) {
        super(id, name, dateCreated, createdBy);
        this.models = models;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}
