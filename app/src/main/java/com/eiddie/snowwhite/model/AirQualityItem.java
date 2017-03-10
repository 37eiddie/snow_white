package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

public class AirQualityItem {

    @SerializedName("khaiGrade")
    public String totalAirGrade;

    @SerializedName("pm10Value")
    public String pm10Value;

    @SerializedName("pm25Value")
    public String pm25Value;

    public String getTotalAirGrade() {
        return totalAirGrade;
    }

    public void setTotalAirGrade(String totalAirGrade) {
        this.totalAirGrade = totalAirGrade;
    }

    public String getPm10Value() {
        return pm10Value;
    }

    public void setPm10Value(String pm10Value) {
        this.pm10Value = pm10Value;
    }

    public String getPm25Value() {
        return pm25Value;
    }

    public void setPm25Value(String pm25Value) {
        this.pm25Value = pm25Value;
    }
}
