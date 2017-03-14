package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MetroInfo {

    @SerializedName("errorMessage")
    HeaderMetroItem headerMetroItem;

    @SerializedName("realtimeArrivalList")
    List<BodyMetroItem> bodyMetroItemList;

    public HeaderMetroItem getHeaderMetroItem() {
        return headerMetroItem;
    }

    public void setHeaderMetroItem(HeaderMetroItem headerMetroItem) {
        this.headerMetroItem = headerMetroItem;
    }

    public List<BodyMetroItem> getBodyMetroItemList() {
        return bodyMetroItemList;
    }

    public void setBodyMetroItemList(List<BodyMetroItem> bodyMetroItemList) {
        this.bodyMetroItemList = bodyMetroItemList;
    }
}
