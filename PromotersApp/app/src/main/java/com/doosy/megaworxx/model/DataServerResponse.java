package com.doosy.megaworxx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataServerResponse<T> extends ServerResponse {

    public DataServerResponse() {
        this.DataList = new ArrayList<>();
    }

    public DataServerResponse(boolean isSuccessful, List<String> messages) {
        super(isSuccessful, messages);
    }
    @SerializedName("dataList")
    @Expose()
    private List<T> DataList;

    @SerializedName("data")
    @Expose()
    private T Data;

    public List<T> getDataList() {
        return DataList;
    }

    public void setDataList(List<T> dataList) {
        DataList = dataList;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
