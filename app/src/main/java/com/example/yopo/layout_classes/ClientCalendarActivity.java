package com.example.yopo.layout_classes;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Session;

import java.util.HashMap;
import java.util.List;

public class ClientCalendarActivity extends AppCompatActivity{

    private CalendarView calendar;
    private TextView date_view;
    private Spinner appointments;
    private String selected_date;

    private Database database;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_calendar_layout);

        calendar = findViewById(R.id.calendar_client);
        date_view = findViewById(R.id.date_info_client);
        appointments = findViewById(R.id.appointments_client_spinner);
        database = Database.getInstance();

        session = Session.getInstance();


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
                    selected_date
                            = dayOfMonth + "/"
                            + (month + 1) + "/" + year;

                    // set this date in TextView for Display
                    date_view.setText(selected_date);

                    List<HashMap<String, Object>> appointments_list = database.get_appointment_info(session.get_session_attribute("username").toString(), selected_date, true);
                    if (appointments_list != null){
                        String[] appointment_info_list = new String[appointments_list.size()];
                        int counter = 0;
                        for (HashMap<String, Object> appointment : appointments_list){
                            String app = appointment.get("business_username") + "-" + appointment.get("date")
                                    + "-" + appointment.get("time")+ "-" + appointment.get("service");
                            appointment_info_list[counter] = app;
                            counter++;
                        }

                        ArrayAdapter<String> appointments_adapter = new ArrayAdapter<String>(ClientCalendarActivity.this,
                                android.R.layout.simple_spinner_item, appointment_info_list);

                        appointments.setAdapter(appointments_adapter);
                    }
                }
            });
    }
}
