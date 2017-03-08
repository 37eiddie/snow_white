package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherItems {

    @SerializedName("item")
    List<WeatherItem> weatherItemList;

    public List<WeatherItem> getWeatherItemList() {
        return weatherItemList;
    }

    public void setWeatherItemList(List<WeatherItem> weatherItemList) {
        this.weatherItemList = weatherItemList;
    }
}
