package com.eiddie.snowwhite.model;

import com.google.gson.annotations.SerializedName;

public class BodyMetroItem {

    @SerializedName("updnLine")
    private String updnLine;

    @SerializedName("trainLineNm")
    private String trainLineNm;

    @SerializedName("btrainSttus")
    private String btrainSttus;

    @SerializedName("recptnDt")
    private String recptnDt;

    @SerializedName("arvlMsg2")
    private String arvlMsg2;

    @SerializedName("arvlMsg3")
    private String arvlMsg3;

    public String getUpdnLine() {
        return updnLine;
    }

    public void setUpdnLine(String updnLine) {
        this.updnLine = updnLine;
    }

    public String getTrainLineNm() {
        return trainLineNm;
    }

    public void setTrainLineNm(String trainLineNm) {
        this.trainLineNm = trainLineNm;
    }

    public String getBtrainSttus() {
        return btrainSttus;
    }

    public void setBtrainSttus(String btrainSttus) {
        this.btrainSttus = btrainSttus;
    }

    public String getRecptnDt() {
        return recptnDt;
    }

    public void setRecptnDt(String recptnDt) {
        this.recptnDt = recptnDt;
    }

    public String getArvlMsg2() {
        return arvlMsg2;
    }

    public void setArvlMsg2(String arvlMsg2) {
        this.arvlMsg2 = arvlMsg2;
    }

    public String getArvlMsg3() {
        return arvlMsg3;
    }

    public void setArvlMsg3(String arvlMsg3) {
        this.arvlMsg3 = arvlMsg3;
    }
}
