package com.eiddie.snowwhite.service;

import com.eiddie.snowwhite.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SunRiseSetService {

    @GET("RiseSetInfoService/getAreaRiseSetInfo?serviceKey=6oROxkNtYkj%2FF8CNNXqLy70feyJsHbKjXJwdH7KnkCqFpwsi9rr8RKJrYfep%2BYVd9vqBso9ffxuggVDTV8%2FKJw%3D%3D&_type=json")
    Call<Weather> getAreaRiseSetInfo(@Query("location") String location, @Query("locdate") String locdate);
}
