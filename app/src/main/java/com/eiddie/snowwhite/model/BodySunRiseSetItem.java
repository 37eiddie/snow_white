package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

public class BodySunRiseSetItem {

    @SerializedName("items")
    SunRiseSetItems sunRiseSetItems;

    @SerializedName("numOfRows")
    int numOfRows;

    @SerializedName("pageNo")
    int pageNo;

    @SerializedName("totalCount")
    int totalCount;

    public SunRiseSetItems getSunRiseSetItems() {
        return sunRiseSetItems;
    }

    public void setSunRiseSetItems(SunRiseSetItems sunRiseSetItems) {
        this.sunRiseSetItems = sunRiseSetItems;
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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
