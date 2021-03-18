package com.doosy.megaworxx.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServerResponse {
    @SerializedName("isSuccessful")
    @Expose()
    private boolean isSuccessful;

    @SerializedName("messages")
    @Expose()
    private List<String> messages;

    public ServerResponse() {
        this.messages = new ArrayList<>();
    }

    public ServerResponse(boolean isSuccessful, List<String> message) {
        this.isSuccessful = isSuccessful;
        this.messages = message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public List<String>  getMessages() {
        return messages;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        String msg = "";
        for (String message: messages ) {
            msg += message +"\'";
        }

        return "ServerResponse{" +
                "isSuccessful='" + isSuccessful + '\'' +
                ", message='" + msg + '\'' +
                '}';
    }
}
