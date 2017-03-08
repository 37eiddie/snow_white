package com.eiddie.snowwhite;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eiddie.snowwhite.adapter.NewsScrollAdapter;
import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalDate date = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MMM dd, E");

        String str = date.toString(dateTimeFormatter);

        TextView textView = (TextView) findViewById(R.id.text_date);
        textView.setText(str);

        CustomAnalogClock customAnalogClock = (CustomAnalogClock) findViewById(R.id.analog_clock);
        customAnalogClock.setAutoUpdate(true);

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
    }
}
