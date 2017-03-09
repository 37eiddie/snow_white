package com.eiddie.snowwhite.service;

import com.eiddie.snowwhite.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("ForecastGrib?serviceKey=6oROxkNtYkj%2FF8CNNXqLy70feyJsHbKjXJwdH7KnkCqFpwsi9rr8RKJrYfep%2BYVd9vqBso9ffxuggVDTV8%2FKJw%3D%3D&nx=59&ny=127&_type=json")
    Call<Weather> getDegree(@Query("base_date") String baseDate, @Query("base_time") String base_time);
}
