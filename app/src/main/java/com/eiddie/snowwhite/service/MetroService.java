package com.eiddie.snowwhite.service;

import com.eiddie.snowwhite.model.MetroInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MetroService {

    @GET("6d5a74656b65646436364a57694870/json/realtimeStationArrival/0/5/가좌")
    Call<MetroInfo> getMetroInfo();
}
