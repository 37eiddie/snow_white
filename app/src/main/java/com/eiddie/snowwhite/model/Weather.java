package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by actmember on 3/8/17.
 */
public class Weather {

    @SerializedName("response")
    ResponseItem responseItem;

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }
}
