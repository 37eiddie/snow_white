package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by actmember on 3/8/17.
 */
public class ResponseSunRiseSetItem {

    @SerializedName("header")
    HeaderItem headerItem;

    @SerializedName("body")
    BodySunRiseSetItem bodySunRiseSetItem;

    public HeaderItem getHeaderItem() {
        return headerItem;
    }

    public void setHeaderItem(HeaderItem headerItem) {
        this.headerItem = headerItem;
    }

    public BodySunRiseSetItem getBodySunRiseSetItem() {
        return bodySunRiseSetItem;
    }

    public void setBodySunRiseSetItem(BodySunRiseSetItem bodySunRiseSetItem) {
        this.bodySunRiseSetItem = bodySunRiseSetItem;
    }
}
