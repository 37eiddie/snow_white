package com.eiddie.snowwhite;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.eiddie.snowwhite.adapter.NewsScrollAdapter;
import com.eiddie.snowwhite.model.AirQuality;
import com.eiddie.snowwhite.model.AirQualityItem;
import com.eiddie.snowwhite.model.SunRiseSet;
import com.eiddie.snowwhite.model.Weather;
import com.eiddie.snowwhite.model.WeatherItem;
import com.eiddie.snowwhite.service.AirQualityService;
import com.eiddie.snowwhite.service.SunRiseSetService;
import com.eiddie.snowwhite.service.WeatherService;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    RSSFeed feed;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private RecyclerView newsRecyclerView;
    private NewsScrollAdapter newsScrollAdapter;

    private final Runnable SCROLLING_RUNNABLE = new Runnable() {

        @Override
        public void run() {
            final int duration = 10;
            final int pixelsToMove = 3;

            newsRecyclerView.scrollBy(pixelsToMove, 0);

            mHandler.postDelayed(this, duration);
        }
    };

    WeatherService weatherService;
    AirQualityService airQualityService;
    SunRiseSetService sunRiseSetService;

    private int rainfall;
    private int sky;
    private LinearLayoutManager layoutManager;
    private String sunRiseTime;
    private String sunSetTime;
    private LocalDateTime localDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        localDateTime = LocalDateTime.now();

        newsRecyclerView = (RecyclerView) findViewById(R.id.marqueList);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsScrollAdapter = new NewsScrollAdapter(new ArrayList<RSSItem>());
        newsRecyclerView.setAdapter(newsScrollAdapter);

        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(!newsRecyclerView.canScrollHorizontally(dx)){
                    newsRecyclerView.setAdapter(new NewsScrollAdapter(feed.getItems()));
                }
            }
        });

        Retrofit weatherRetrofit = new Retrofit.Builder()
                .baseUrl("http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        weatherService = weatherRetrofit.create(WeatherService.class);

        Retrofit airQualityRetrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.airkorea.or.kr/openapi/services/rest/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        airQualityService = airQualityRetrofit.create(AirQualityService.class);

        Retrofit sunRiseSetRetrofit = new Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/B090041/openapi/service/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        sunRiseSetService = sunRiseSetRetrofit.create(SunRiseSetService.class);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final String uri = "https://news.google.com/news?cf=all&hl=ko&pz=1&ned=kr&output=rss";

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                RSSReader reader = new RSSReader();
                try {
                    feed = reader.load(uri);
                } catch (RSSReaderException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                newsScrollAdapter.updateData(feed.getItems());
                mHandler.post(SCROLLING_RUNNABLE);
            }

        }.execute();

        getAreaRiseSetInfo();
        getAirQualityInfo();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    private void getAirQualityInfo() {
        Call<AirQuality> call = airQualityService.getAirQualityInfo();
        call.enqueue(new Callback<AirQuality>() {
            @Override
            public void onResponse(Call<AirQuality> call, Response<AirQuality> response) {
                List<AirQualityItem> airQualityItemList = response.body().getAirQualityItemList();

                AirQualityItem airQualityItem = airQualityItemList.get(0);

                int airQuality = 0;
                if(!"".equals(airQualityItem.getTotalAirGrade())){
                    airQuality = Integer.parseInt(airQualityItem.getTotalAirGrade());
                }

                TextView textDustGrade = (TextView) findViewById(R.id.text_dust_grade);
                TextView textPm10Value= (TextView) findViewById(R.id.text_pm_10_value);
                textPm10Value.setText(airQualityItem.getPm10Value()+" ㎍/㎥");

                switch (airQuality){
                    case 1:
                        textDustGrade.setText("좋음");
                        break;
                    case 2:
                        textDustGrade.setText("보통");
                        break;
                    case 3:
                        textDustGrade.setText("나쁨");
                        break;
                    case 4:
                        textDustGrade.setText("매우나쁨");
                        break;
                    default:
                        textDustGrade.setText("보통");
                        break;
                }
            }

            @Override
            public void onFailure(Call<AirQuality> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void getWeatherInfo() {
        // - 하늘상태(SKY) 코드 : 맑음(1), 구름조금(2), 구름많음(3), 흐림(4)
        // - 강수형태(PTY) 코드 : 없음(0), 비(1), 비/눈(2), 눈(3) 여기서 비/눈은 비와 눈이 섞여 오는 것을 의미 (진눈개비)
        Call<Weather> call = weatherService.getDegree(LocalDate.now().toString("yyyyMMdd"), LocalDateTime.now().minusHours(1).toString("HHmm"));
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                TextView degreeTextView = (TextView) findViewById(R.id.text_temperature);

                List<WeatherItem> weatherItemList = response.body().getResponseItem().getBodyItem().getItems().getWeatherItemList();
                for (WeatherItem weatherItem : weatherItemList) {
                    if("T1H".equals(weatherItem.getCategory())){
                        degreeTextView.setText(weatherItem.getObsrValue() + " °");
                    }

                    if("PTY".equals(weatherItem.getCategory())){
                        rainfall = (int) weatherItem.getObsrValue();
                    }

                    if("SKY".equals(weatherItem.getCategory())){
                        sky = (int) weatherItem.getObsrValue();
                    }
                }

                ImageView weatherIcon = (ImageView) findViewById(R.id.weather_icon);
                String currentTime = LocalDateTime.now().toString("HHMMss");
                if (0 != rainfall){
                    switch (rainfall) {
                        case 1:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rain));
                            break;
                        case 2:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.sleet));
                            break;
                        case 3:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.snow));
                            break;
                    }
                } else {
                    switch (sky) {
                        case 1:
                            if(currentTime.compareTo(sunRiseTime) > 0 && currentTime.compareTo(sunSetTime) < 0){
                                weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.clear_day));
                            }else{
                                weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.clear_night));
                            }
                            break;
                        case 2:
                            if(currentTime.compareTo(sunRiseTime) > 0 && currentTime.compareTo(sunSetTime) < 0){
                                weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.partly_cloudy_day));
                            }else{
                                weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.partly_cloudy_night));
                            }

                            break;
                        case 3:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cloudy));
                            break;
                        case 4:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cloudy));
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.e("error",t.getMessage());
            }
        });
    }

    private void getAreaRiseSetInfo(){
        Call<SunRiseSet> call = sunRiseSetService.getAreaRiseSetInfo("서울", LocalDate.now().toString("yyyyMMdd"));
        call.enqueue(new Callback<SunRiseSet>() {
            @Override
            public void onResponse(Call<SunRiseSet> call, Response<SunRiseSet> response) {
                sunRiseTime = response.body().getResponseItem().getBodySunRiseSetItem().getSunRiseSetItems().getSunRiseSetItem().getSunrise();
                sunSetTime = response.body().getResponseItem().getBodySunRiseSetItem().getSunRiseSetItems().getSunRiseSetItem().getSunset();

                getWeatherInfo();
            }

            @Override
            public void onFailure(Call<SunRiseSet> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}