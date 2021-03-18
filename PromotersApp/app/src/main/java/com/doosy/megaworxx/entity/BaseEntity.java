package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseEntity implements Serializable {


    @SerializedName("id")
    @Expose()
    private String id;

    @SerializedName("name")
    @Expose()
    private String name;

    @SerializedName("dateCreated")
    @Expose()
    private String dateCreated;

    @SerializedName("createdBy")
    @Expose()
    private String createdBy;

    public BaseEntity(String id, String name, String dateCreated, String createdBy) {
        this.id = id;
        this.name = name;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


}
