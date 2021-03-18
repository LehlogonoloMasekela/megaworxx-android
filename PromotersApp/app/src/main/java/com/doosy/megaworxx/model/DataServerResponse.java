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
        this.DataList = new ArrayList<>();
    }

    public DataServerResponse(T data) {
        this.DataList = new ArrayList<>();
        Data = data;
    }

    @Expose()
    private T Data;

    @SerializedName("data")
    @Expose()
    private List<T> DataList;

/*
    @Expose()
    private ClientToken clientToken;

    public ClientToken getClientToken() {
        return clientToken;
    }
*/

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
