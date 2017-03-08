package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by actmember on 3/8/17.
 */
public class HeaderItem {

    @SerializedName("resultCode")
    String resultCode;

    @SerializedName("resultMsg")
    String resultMsg;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
