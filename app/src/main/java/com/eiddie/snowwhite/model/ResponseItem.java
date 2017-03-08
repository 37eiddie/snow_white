package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by actmember on 3/8/17.
 */
public class ResponseItem {

    @SerializedName("header")
    HeaderItem headerItem;

    @SerializedName("body")
    BodyItem bodyItem;

    public HeaderItem getHeaderItem() {
        return headerItem;
    }

    public void setHeaderItem(HeaderItem headerItem) {
        this.headerItem = headerItem;
    }

    public BodyItem getBodyItem() {
        return bodyItem;
    }

    public void setBodyItem(BodyItem bodyItem) {
        this.bodyItem = bodyItem;
    }
}
