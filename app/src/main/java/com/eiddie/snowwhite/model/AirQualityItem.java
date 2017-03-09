package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

public class AirQualityItem {

    @SerializedName("khaiGrade")
    public int totalAirGrade;

    @SerializedName("pm10Value")
    public int pm10Value;

    @SerializedName("pm25Value")
    public int pm25Value;

    public int getTotalAirGrade() {
        return totalAirGrade;
    }

    public void setTotalAirGrade(int totalAirGrade) {
        this.totalAirGrade = totalAirGrade;
    }

    public int getPm25Value() {
        return pm25Value;
    }

    public void setPm25Value(int pm25Value) {
        this.pm25Value = pm25Value;
    }

    public int getPm10Value() {
        return pm10Value;
    }

    public void setPm10Value(int pm10Value) {
        this.pm10Value = pm10Value;
    }
}
