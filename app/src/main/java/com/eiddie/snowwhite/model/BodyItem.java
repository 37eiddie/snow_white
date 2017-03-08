package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

public class BodyItem {

    @SerializedName("items")
    WeatherItems items;

    @SerializedName("numOfRows")
    int numOfRows;

    @SerializedName("pageNo")
    int pageNo;

    @SerializedName("totalCount")
    int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(int numOfRows) {
        this.numOfRows = numOfRows;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public WeatherItems getItems() {
        return items;
    }

    public void setItems(WeatherItems items) {
        this.items = items;
    }
}
