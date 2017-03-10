package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

public class SunRiseSet {

    @SerializedName("response")
    ResponseSunRiseSetItem responseItem;

    public ResponseSunRiseSetItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseSunRiseSetItem responseItem) {
        this.responseItem = responseItem;
    }
}
