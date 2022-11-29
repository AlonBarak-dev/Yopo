package com.example.yopo.layout_classes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.yopo.R;
import com.example.yopo.data_classes.BusinessRegisterValidator;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Session;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;


public class ClientAppointmentScheduleActivity extends AppCompatActivity{

    private CalendarView calendar;
    private Spinner hours;
    private Button save;

    private Session session;
    private Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_schedule_layout);

        calendar = findViewById(R.id.calendar_dates_schedule);
        hours = findViewById(R.id.avaliable_hours_spinner);
        database = Database.getInstance();

        String[] list_of_hours = new String[24];

        // get data from session
        session = Session.getInstance();
        String client_username = (String) session.get_session_attribute("username");
        String business_username = (String) session.get_session_attribute("business_username");

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

                // this will serve as a key in the future
                // in order to extract the appointments from the database.
                String Date = dayOfMonth + "/" + (month + 1) + "/" + year;

                for(int i = 0; i < 24; i++){
                    String start_time = i + ":00";
                    String end_time = ((i+1) % 24) + ":00";
                    String full_time = start_time + "--" + end_time;
                    list_of_hours[i] = full_time;
                }

                ArrayAdapter<String> hours_adapter = new ArrayAdapter<String>(ClientAppointmentScheduleActivity.this,
                        android.R.layout.simple_spinner_item, list_of_hours);


                hours.setAdapter(hours_adapter);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> new_appointment = new HashMap<>();


            }
        });






    }








}
