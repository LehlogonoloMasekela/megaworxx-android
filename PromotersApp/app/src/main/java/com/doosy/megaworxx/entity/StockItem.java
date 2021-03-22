package com.doosy.megaworxx.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StockItem extends BaseEntity implements Serializable {

    @SerializedName("makeId")
    @Expose()
    private String makeId;

    @SerializedName("modelId")
    @Expose()
    private String modelId;

    @SerializedName("yearCreated")
    @Expose()
    private String yearCreated;

    @SerializedName("make")
    @Expose()
    private Make make;

    @SerializedName("model")
    @Expose()
    private Model model;

    public StockItem(String id, String name, String dateCreated, String createdBy,
                     String makeId, String modelId, String yearCreated) {
        super(id, name, dateCreated, createdBy);
        this.makeId = makeId;
        this.modelId = modelId;
        this.yearCreated = yearCreated;
    }

    public StockItem(String id, String name) {
        super(id, name);
    }

    public String getMakeId() {
        return makeId;
    }

    public void setMakeId(String makeId) {
        this.makeId = makeId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getYearCreated() {
        return yearCreated;
    }

    public void setYearCreated(String yearCreated) {
        this.yearCreated = yearCreated;
    }

    public Make getMake() {
        return make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
