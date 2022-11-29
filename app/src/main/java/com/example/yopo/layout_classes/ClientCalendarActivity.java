package com.example.yopo.layout_classes;
import android.os.Bundle;

import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;

public class ClientCalendarActivity extends AppCompatActivity{

    private CalendarView calendar;
    private TextView date_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_calendar_layout);

        calendar = findViewById(R.id.calendar_client);
        date_view = findViewById(R.id.date_info_client);

        // Add Listener in calendar
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                // In this Listener have one method
                // and in this method we will
                // get the value of DAYS, MONTH, YEARS
                public void onSelectedDayChange(
                        CalendarView view,
                        int year,
                        int month,
                        int dayOfMonth)
                {

                    // Store the value of date with
                    // format in String type Variable
                    // Add 1 in month because month
                    // index is start with 0.
                    // this will serve as a key in the future
                    // in order to extract the appointments from the database.
                    String Date
                            = dayOfMonth + "-"
                            + (month + 1) + "-" + year;

                    // set this date in TextView for Display
                    date_view.setText(Date);
                }
            });
    }
}
