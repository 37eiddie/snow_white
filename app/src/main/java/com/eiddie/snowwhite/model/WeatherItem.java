package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

public class WeatherItem {

    @SerializedName("baseDate")
    private String baseDate;

    @SerializedName("baseTime")
    private String baseTime;

    @SerializedName("category")
    private String category;

    @SerializedName("nx")
    private int nx;

    @SerializedName("ny")
    private int ny;

    @SerializedName("obsrValue")
    private float obsrValue;

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public String getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNx() {
        return nx;
    }

    public void setNx(int nx) {
        this.nx = nx;
    }

    public int getNy() {
        return ny;
    }

    public void setNy(int ny) {
        this.ny = ny;
    }

    public float getObsrValue() {
        return obsrValue;
    }

    public void setObsrValue(float obsrValue) {
        this.obsrValue = obsrValue;
    }
}
