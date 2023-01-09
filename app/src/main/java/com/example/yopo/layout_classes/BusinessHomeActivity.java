package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.ServerFactory;
import com.example.yopo.data_classes.Session;
import com.example.yopo.interfaces.Server;

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

    private Session session;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_home_layout);

        initializeServerAndSession();
        initializeFields();
        initializeButtonsOnClickEvents();
    }

    private void initializeServerAndSession() {
        try {
            server = ServerFactory.getServer("firestore");
        } catch (ClassNotFoundException e) {
            Log.e("ServerFactory", e.toString());
        }

        session = Session.getInstance();
        String username = getIntent().getStringExtra("username");
        session.add_session_attribute("username", username);
    }

    private void initializeFields() {
        logout = findViewById(R.id.logout_button_business);
        edit_schedule = findViewById(R.id.edit_schedule_business_button);
        calendar = findViewById(R.id.calendar_dates_scehdule);
        date_view = findViewById(R.id.date_info_business_home);
        appointments = findViewById(R.id.appointments_business_spinner);
        profile_button = findViewById(R.id.home_2_profile_button);
    }

    private void initializeButtonsOnClickEvents() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logout from the profile -> back to Main page
                Toast.makeText(BusinessHomeActivity.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(BusinessHomeActivity.this, MainActivity.class);
                // clear the session when logging out
                session.clear_session();
                startActivity(i);
            }
        });

        edit_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to the edit screen where the user can edit it schedule.
                Toast.makeText(BusinessHomeActivity.this, "Loading Schedule...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(BusinessHomeActivity.this, BusinessScheduleActivity.class);
                startActivity(i);
            }
        });

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BusinessHomeActivity.this, "Loading Profile...", Toast.LENGTH_SHORT).show();
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

                        initializeSpinner(selected_date);

                    }
                });
    }

    private void initializeSpinner(String selected_date) {
        List<HashMap<String, Object>> appointments_list = server.get_appointment_info((String) session.get_session_attribute("username"), selected_date, false);
        if (appointments_list != null && !appointments_list.isEmpty()) {
            showAppointments(appointments_list);
        } else {
            noAppointments();
        }
    }

    private String[] createAppointmentInfoList(List<HashMap<String, Object>> appointments_list) {
        String[] appointment_info_list = new String[appointments_list.size()];
        int counter = 0;
        for (HashMap<String, Object> appointment : appointments_list) {
            String client_name = server.get_client_info(appointment.get("client_username").toString()).get("first_name").toString();
            String service_name = (String) server.get_service((String) appointment.get("service")).get("service");
            String app = client_name + " ordered: " + service_name + ", in:" + appointment.get("date")
                    + " " + appointment.get("time");
            appointment_info_list[counter] = app;
            counter++;
        }

        return appointment_info_list;
    }

    private void showAppointments(List<HashMap<String, Object>> appointments_list) {
        String[] appointment_info_list = createAppointmentInfoList(appointments_list);

        ArrayAdapter<String> appointments_adapter = new ArrayAdapter<String>(BusinessHomeActivity.this,
                android.R.layout.simple_spinner_item, appointment_info_list);

        appointments.setAdapter(appointments_adapter);
    }

    private void noAppointments() {
        String[] appointment_info_list = new String[1];
        appointment_info_list[0] = "No appointments";
        ArrayAdapter<String> appointments_adapter = new ArrayAdapter<String>(BusinessHomeActivity.this,
                android.R.layout.simple_spinner_item, appointment_info_list);

        appointments.setAdapter(appointments_adapter);
    }
}
