package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Session;

import java.util.HashMap;
import java.util.List;

public class BusinessHomeActivity extends AppCompatActivity {

    private ImageButton logout;
    private ImageButton edit_schedule;
    private CalendarView calendar;
    private TextView date_view;
    private Spinner appointments;
    private Button profile_button;
    private String selected_date;

    private Database database;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_home_layout);

        logout = findViewById(R.id.logout_button_business);
        edit_schedule = findViewById(R.id.edit_schedule_business_button);
        calendar = findViewById(R.id.calendar_dates_scehdule);
        date_view = findViewById(R.id.date_info_business_home);
        appointments = findViewById(R.id.appointments_business_spinner);
        profile_button = findViewById(R.id.home_2_profile_button);
        // extract the database object which is a singleton
        database = Database.getInstance();

        // get user info to use in database
        String username = getIntent().getStringExtra("username");
        session = Session.getInstance();
        session.add_session_attribute("username", username);

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

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessHomeActivity.this, BusinessProfileActivity.class);
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
                            int dayOfMonth) {
                        // this will serve as a key in the future
                        // in order to extract the appointments from the database.
                        selected_date
                                = dayOfMonth + "/"
                                + (month + 1) + "/" + year;

                        // set this date in TextView for Display
                        date_view.setText(selected_date);

                        List<HashMap<String, Object>> appointments_list = database.get_appointment_info(username, selected_date, false);
                        if (appointments_list != null){
                            String[] appointment_info_list = new String[appointments_list.size()];
                            int counter = 0;
                            for (HashMap<String, Object> appointment : appointments_list){
                                String app = appointment.get("client_username") + "-" + appointment.get("date")
                                        + "-" + appointment.get("time");
                                appointment_info_list[counter] = app;
                                counter++;
                            }

                            ArrayAdapter<String> appointments_adapter = new ArrayAdapter<String>(BusinessHomeActivity.this,
                                    android.R.layout.simple_spinner_item, appointment_info_list);

                            appointments.setAdapter(appointments_adapter);
                        }

                    }
                });
    }
}
