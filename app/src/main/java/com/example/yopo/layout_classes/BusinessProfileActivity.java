package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.yopo.R;

import com.example.yopo.data_classes.Database;

import com.example.yopo.data_classes.Session;

import java.util.HashMap;
import java.util.Calendar;
import java.util.List;

public class BusinessProfileActivity extends AppCompatActivity {

    private Database database;
    private Session session;

    private TextView username;
    private TextView email;
    private TextView business_address;
    private TextView categories;
    private TextView today_appointments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_profile_layout);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        business_address = findViewById(R.id.business_address);
        categories = findViewById(R.id.category);


        // extract database and session
        database = Database.getInstance();
        session = Session.getInstance();

        // get business document
        HashMap<String, Object> business_info = database.get_business_info(session.get_session_attribute("username").toString());

        // show business details in layout
        username.setText(
                business_info.get("username").toString()
        );

        email.setText(
                business_info.get("email").toString()
        );

        business_address.setText(
                business_info.get("city").toString() + "-" +
                business_info.get("street").toString() + "-" +
                business_info.get("home_num").toString() + "-" +
                business_info.get("floor").toString()
        );

        categories.setText(
                business_info.get("category") + "-" +
                business_info.get("subcategory")
        );

//        String currDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) +"/"+
//                          Calendar.getInstance().get(Calendar.MONTH) +"/"+
//                          Calendar.getInstance().get(Calendar.YEAR);
//        Log.i("BusinessProfileActivity", currDate);
//        List<HashMap<String, Object>> appointments = database.get_appointment_info(username.getText().toString(), currDate, false);
//        Log.i("BusinessProfileActivity", appointments.toString());










    }
}