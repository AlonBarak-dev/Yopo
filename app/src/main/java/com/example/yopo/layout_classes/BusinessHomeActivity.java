package com.example.yopo.layout_classes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;

public class BusinessHomeActivity extends AppCompatActivity {

    private ImageButton logout;
    private ImageButton edit_schedule;
    private CalendarView calendar;
    private TextView date_view;

    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_home_layout);

        logout = findViewById(R.id.logout_button_business);
        edit_schedule = findViewById(R.id.edit_schedule_business_button);
        calendar = findViewById(R.id.Calendar_business_home_layout);
        date_view = findViewById(R.id.date_info_business_home);
        // extract the database object which is a singleton
        database = Database.getInstance();
        // get user info to use in database
        String username = getIntent().getStringExtra("username");

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logout from the profile -> back to Main page
                Intent i = new Intent(BusinessHomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        edit_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to the edit screen where the user can edit it schedule.
                Intent i = new Intent(BusinessHomeActivity.this, BusinessScheduleActivity.class);
                startActivity(i);
            }
        });

        // Add Listener in calendar
        calendar.setOnDateChangeListener(
            new CalendarView
                    .OnDateChangeListener() {
                // In this Listener have one method
                // and in this method we will
                // get the value of DAYS, MONTH, YEAR
                @Override
                public void onSelectedDayChange(
                        CalendarView view,
                        int year,
                        int month,
                        int dayOfMonth)
                {
                    // this will serve as a key in the future
                    // in order to extract the appointments from the database.
                    String Date
                            = username + ":"+ dayOfMonth + "-"
                            + (month + 1) + "-" + year;

                    // set this date in TextView for Display
                    date_view.setText(Date);
                }
            });
    }
}
