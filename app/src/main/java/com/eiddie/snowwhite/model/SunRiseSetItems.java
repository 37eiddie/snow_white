package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

public class SunRiseSetItems {

    @SerializedName("item")
    SunRiseSetItem sunRiseSetItem;

    public SunRiseSetItem getSunRiseSetItem() {
        return sunRiseSetItem;
    }

    public void setSunRiseSetItem(SunRiseSetItem sunRiseSetItem) {
        this.sunRiseSetItem = sunRiseSetItem;
    }
}
