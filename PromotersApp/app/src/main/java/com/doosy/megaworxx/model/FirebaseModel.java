package com.doosy.megaworxx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirebaseModel {
    @SerializedName("userId")
    @Expose()
    private String userId;

    @SerializedName("googleToken")
    @Expose()
    private String googleToken;

    public FirebaseModel(String userId, String googleToken) {
        this.userId = userId;
        this.googleToken = googleToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public void setGoogleToken(String googleToken) {
        this.googleToken = googleToken;
    }
}
