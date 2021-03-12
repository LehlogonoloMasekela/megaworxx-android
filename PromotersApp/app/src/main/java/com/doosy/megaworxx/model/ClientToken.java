package com.doosy.megaworxx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientToken {
    @SerializedName("access_token")
    @Expose()
    private String accessToken;

    @SerializedName("token_type")
    @Expose()
    private String tokenType;

    @SerializedName("scope")
    @Expose()
    private String scope;

    @SerializedName("expires_in")
    @Expose()
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getScope() {
        return scope;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    @Override
    public String toString() {
        return "ClientToken{" +
                "accessToken='" + accessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", scope='" + scope + '\'' +
                ", expiresIn=" + expiresIn +
                '}';
    }
}
