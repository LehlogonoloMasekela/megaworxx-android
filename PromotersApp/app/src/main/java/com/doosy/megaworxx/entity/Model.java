package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Model extends BaseEntity implements Serializable {

    @SerializedName("makeId")
    @Expose()
    private String makeId;

    public Model(String id, String name, String dateCreated, String createdBy) {
        super(id, name, dateCreated, createdBy);
    }

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }
}
