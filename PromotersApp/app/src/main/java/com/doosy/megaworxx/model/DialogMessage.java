package com.doosy.megaworxx.model;

import java.io.Serializable;

public class DialogMessage implements Serializable {
    private String header;
    private String message;
    private int icon;

    public DialogMessage(String header, String message) {
        this.header = header;
        this.message = message;
    }

    public DialogMessage(String header, String message, int icon) {
        this.header = header;
        this.message = message;
        this.icon = icon;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
