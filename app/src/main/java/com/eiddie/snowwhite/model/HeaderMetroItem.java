package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by actmember on 3/13/17.
 */
public class HeaderMetroItem {

    @SerializedName("status")
    String status;
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;
    @SerializedName("link")
    String link;
    @SerializedName("developerMessage")
    String developerMessage;
    @SerializedName("total")
    String total;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
