package com.eiddie.snowwhite;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

public class MainActivity extends Activity {

    RSSFeed feed;
    private TextView marqueText;

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

        marqueText = (TextView) this.findViewById(R.id.text_news_marque);

        marqueText.setSelected(true);
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
                StringBuffer newsBuffer = new StringBuffer();
                newsBuffer.append(feed.getItems().get(0).getTitle()).append("   ");
                newsBuffer.append(feed.getItems().get(1).getTitle()).append("   ");
                newsBuffer.append(feed.getItems().get(2).getTitle()).append("   ");
                newsBuffer.append(feed.getItems().get(3).getTitle()).append("   ");
                newsBuffer.append(feed.getItems().get(4).getTitle()).append("   ");

                marqueText.setText(newsBuffer.toString());
            }

        }.execute();

    }
}
