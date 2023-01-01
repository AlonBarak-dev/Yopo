package com.example.yopo.layout_classes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Server;
import com.example.yopo.data_classes.Session;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ClientAppointmentScheduleActivity extends AppCompatActivity {

    private CalendarView calendar;
    private Spinner hours;
    private Spinner services;
    private Button save;

    private String selected_date;

    private Session session;
    private Database database;
    private Server server;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_schedule_layout);

        calendar = findViewById(R.id.calendar_dates_schedule);
        hours = findViewById(R.id.avaliable_hours_spinner);
        services = findViewById(R.id.service_spinner_sched);
        save = findViewById(R.id.save_appointment_button);

        database = Database.getInstance();
        server = Server.getInstance();

        String[] list_of_hours = new String[24];

        // get data from session
        session = Session.getInstance();
        String client_username = (String) session.get_session_attribute("username");
        String business_username = (String) session.get_session_attribute("business_username");

        // retrieve the services of the desired business and present them in a spinner
        List<HashMap<String, Object>> list_of_services = server.get_services(business_username);
        if (list_of_services != null) {
            String[] services_strings = new String[list_of_services.size()];
            int counter = 0;
            for (HashMap<String, Object> service : list_of_services) {
                String serv_str = service.get("service") + " : " + service.get("price");
                services_strings[counter] = serv_str;
                counter++;
            }

            ArrayAdapter<String> services_adapter = new ArrayAdapter<String>(ClientAppointmentScheduleActivity.this,
                    android.R.layout.simple_spinner_item, services_strings);

            services.setAdapter(services_adapter);
        }


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
                    int dayOfMonth) {

                // this will serve as a key in the future
                // in order to extract the appointments from the database.
                selected_date = dayOfMonth + "/" + (month + 1) + "/" + year;
                HashMap<String, Object> open_hours = server.get_open_range_by_full_date(business_username, selected_date);

                // retrieve the business appointments for the selected day and look for taken slots.
                List<HashMap<String, Object>> taken_appointments = server.get_appointment_info(business_username, selected_date, false);
                if (taken_appointments != null) {
                    String[] taken_hours = new String[taken_appointments.size()];
                    int counter = 0;
                    for (HashMap<String, Object> appointment : taken_appointments) {
                        Log.d("appointment", appointment.toString());
                        taken_hours[counter] = (String) appointment.get("time");
                        counter++;
                    }

                    // add taken appointments as Taken: <time>
                    for (int i = 0; i < 24; i++) {
                        String start_time = i + ":00";
                        String end_time = ((i + 1) % 24) + ":00";
                        String full_time = start_time + "--" + end_time;
                        boolean free_hour = true;
                        for (int j = 0; j < taken_appointments.size(); j++) {
                            if (full_time.equals(taken_hours[j])) {
                                free_hour = false;
                            }
                        }
                        String key = (i < 10) ? "0" + i + "-00" : "" + i + "-00";
                        if (open_hours != null && !(boolean)open_hours.get(key))
                            list_of_hours[i] = "Closed: " + full_time;
                        else if (free_hour)
                            list_of_hours[i] = "Open: " + full_time;
                        else
                            list_of_hours[i] = "Taken: " + full_time;
                    }
                } else {
                    for (int i = 0; i < 24; i++) {

                        String start_time = i + ":00";
                        String end_time = ((i + 1) % 24) + ":00";
                        String full_time = start_time + "--" + end_time;
                        String key = (i < 10) ? "0" + i + "-00" : "" + i + "-00";
                        if (open_hours != null && !(boolean)open_hours.get(key))
                            list_of_hours[i] = "Closed: " + full_time;
                        else
                            list_of_hours[i] = "Open: " + full_time;
                    }
                }

                // init the spinner for hours
                ArrayAdapter<String> hours_adapter = new ArrayAdapter<String>(ClientAppointmentScheduleActivity.this,
                        android.R.layout.simple_spinner_item, list_of_hours);

                hours.setAdapter(hours_adapter);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO add treatment type and users Names.
                // save the appointment to the Database
                if (selected_date != null && hours.getSelectedItem().toString().contains("Open")) {
                    HashMap<String, Object> new_appointment = new HashMap<>();
                    new_appointment.put("client_username", client_username);
                    new_appointment.put("business_username", business_username);
                    new_appointment.put("date", selected_date);
                    new_appointment.put("time", hours.getSelectedItem().toString().split(": ")[1]);
                    String service = services.getSelectedItem().toString().split(" :")[0];
                    new_appointment.put("service", business_username + "-" +service);    // service ID
                    String [] selected_date_id = selected_date.split("/");
                    new_appointment.put("appointment_id", business_username + "-" + client_username + "-"  + selected_date_id[0] + "-" + selected_date_id[1] + "-" + selected_date_id[2] +"-"+ hours.getSelectedItem().toString());  // appointment ID
                    if (server.add_new_appointment(new_appointment)) {
                        Toast.makeText(ClientAppointmentScheduleActivity.this, "Appointment set successfully!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ClientAppointmentScheduleActivity.this, "Please choose available date and hour!", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


}
