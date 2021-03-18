package com.doosy.megaworxx.model;

import com.doosy.megaworxx.entity.BaseEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusModel extends BaseEntity {

    @SerializedName("checkInTime")
    @Expose()
    private String checkInTime;

    @SerializedName("checkOutTime")
    @Expose()
    private String checkOutTime;

    @SerializedName("checkInStatus")
    @Expose()
    private int checkInStatus;

    @SerializedName("checkOutStatus")
    @Expose()
    private int checkOutStatus;

    public StatusModel(String id, String name, String dateCreated, String createdBy,
                       String checkInTime, String checkOutTime, int checkInStatus, int checkOutStatus) {
        super(id, name, dateCreated, createdBy);
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.checkInStatus = checkInStatus;
        this.checkOutStatus = checkOutStatus;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public int getCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(int checkInStatus) {
        this.checkInStatus = checkInStatus;
    }

    public int getCheckOutStatus() {
        return checkOutStatus;
    }

    public void setCheckOutStatus(int checkOutStatus) {
        this.checkOutStatus = checkOutStatus;
    }

    @Override
    public String toString() {
        return "StatusModel{" +
                "checkInTime='" + checkInTime + '\'' +
                ", checkOutTime='" + checkOutTime + '\'' +
                ", checkInStatus=" + checkInStatus +
                ", checkOutStatus=" + checkOutStatus +
                '}';
    }
}
