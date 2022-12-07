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
    private Spinner services;
    private Button save;

    private String selected_date;

    private Session session;
    private Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_schedule_layout);

        calendar = findViewById(R.id.calendar_dates_schedule);
        hours = findViewById(R.id.avaliable_hours_spinner);
        services = findViewById(R.id.service_spinner_sched);
        save = findViewById(R.id.save_appointment_button);
        database = Database.getInstance();

        String[] list_of_hours = new String[24];

        // get data from session
        session = Session.getInstance();
        String client_username = (String) session.get_session_attribute("username");
        String business_username = (String) session.get_session_attribute("business_username");


        List<HashMap<String, Object>> list_of_services = database.get_services(business_username);
        if (list_of_services != null){
            String[] services_strings = new String[list_of_services.size()];
            int counter = 0;
            for (HashMap<String, Object> service: list_of_services){
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
                    int dayOfMonth)
            {

                // this will serve as a key in the future
                // in order to extract the appointments from the database.
                selected_date = dayOfMonth + "/" + (month + 1) + "/" + year;

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
                // TODO add treatment type and users Names.

                if (selected_date != null){
                    HashMap<String, Object> new_appointment = new HashMap<>();
                    new_appointment.put("client_username", client_username);
                    new_appointment.put("business_username", business_username);
                    new_appointment.put("date", selected_date);
                    new_appointment.put("time", hours.getSelectedItem());
                    new_appointment.put("service", services.getSelectedItem());
                    if(database.add_new_appointment(new_appointment)){
                        Toast.makeText(ClientAppointmentScheduleActivity.this, "Appointment set successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(ClientAppointmentScheduleActivity.this, "Please choose date", Toast.LENGTH_SHORT).show();
                }



            }
        });






    }








}
