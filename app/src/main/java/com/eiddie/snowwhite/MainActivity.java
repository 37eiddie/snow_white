package com.eiddie.snowwhite;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.tomerrosenfeld.customanalogclockview.CustomAnalogClock;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainActivity extends Activity {

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
    }
}
