package com.eiddie.snowwhite;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eiddie.snowwhite.adapter.NewsScrollAdapter;
import com.eiddie.snowwhite.model.Weather;
import com.eiddie.snowwhite.model.WeatherItem;
import com.eiddie.snowwhite.service.WeatherService;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
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
    private boolean foundTotalPixel = true;
    private int totalItemCount;
    private int totalMovedPixel;
    private int totalPixel;
    private NewsScrollAdapter newsScrollAdapter;

    //http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib?serviceKey=6oROxkNtYkj%2FF8CNNXqLy70feyJsHbKjXJwdH7KnkCqFpwsi9rr8RKJrYfep%2BYVd9vqBso9ffxuggVDTV8%2FKJw%3D%3D&base_date=20170308&base_time=0500&nx=59&ny=127

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
    private int rainfall;
    private int sky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalDate date = LocalDate.now();

        TextView textDateView = (TextView) findViewById(R.id.text_date);
        TextView textDayView = (TextView) findViewById(R.id.text_day);
        textDayView.setText(date.toString(DateTimeFormat.forPattern("EEEE")));
        textDateView.setText(date.toString(DateTimeFormat.forPattern("MMMM dd")));

        newsScrollAdapter = new NewsScrollAdapter(new ArrayList<RSSItem>());

        newsRecyclerView = (RecyclerView) findViewById(R.id.marqueList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setAdapter(newsScrollAdapter);

        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalMovedPixel = totalMovedPixel + dx;
                totalItemCount = layoutManager.getItemCount();
                if (foundTotalPixel) {
                    if (totalItemCount > 2) {
                        View headerView = layoutManager.getChildAt(0);
                        View itemView = layoutManager.getChildAt(1);

                        if (itemView != null && headerView != null) {
                            totalPixel = ((totalItemCount - 2) * itemView.getWidth()) + (1 * headerView.getWidth());
                            foundTotalPixel = false;
                        }
                    }
                }

                if (!foundTotalPixel && totalMovedPixel >= totalPixel) {
                    newsRecyclerView.setAdapter(new NewsScrollAdapter(feed.getItems()));
                    totalMovedPixel = 0;
                }
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        weatherService = retrofit.create(WeatherService.class);
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

        Call<Weather> call = weatherService.getDegree();
        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                TextView degreeTextView = (TextView) findViewById(R.id.text_degree);

                List<WeatherItem> weatherItemList = response.body().getResponseItem().getBodyItem().getItems().getWeatherItemList();
                for (WeatherItem weatherItem : weatherItemList) {
                    if("T1H".equals(weatherItem.getCategory())){
                        Log.d("degree",""+weatherItem.getObsrValue());
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

                if (0 != rainfall){
                    switch (rainfall) {
                        case 1:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_weather_rain));
                            break;
                        case 2:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_weather_rain_snow));
                            break;
                        case 3:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_weather_snow));
                            break;
                    }
                } else {
                    switch (sky) {
                        case 1:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_weather_sunny));
                            break;
                        case 2:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_weather_little_cloudy));
                            break;
                        case 3:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_weather_cloudy));
                            break;
                        case 4:
                            weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_weather_much_cloudy));
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
}