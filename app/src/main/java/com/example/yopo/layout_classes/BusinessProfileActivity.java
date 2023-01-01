package com.example.yopo.layout_classes;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Server;
import com.example.yopo.data_classes.Session;

import java.util.Calendar;
import java.util.HashMap;

public class BusinessProfileActivity extends AppCompatActivity {

    private Database database;
    private Session session;
    private Server server;

    private TextView username, email, business_address, categories, today_appointments, phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_profile_layout);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        business_address = findViewById(R.id.business_address);
        phone_number = findViewById(R.id.phone_number);
        categories = findViewById(R.id.category);
        today_appointments = findViewById(R.id.today_appointments_num);


        // extract database and session
        database = Database.getInstance();
        server = Server.getInstance();
        session = Session.getInstance();

        // get business document
        HashMap<String, Object> business_info = server.get_business_info(session.get_session_attribute("username").toString());

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

        phone_number.setText(
                business_info.get("phone").toString()
        );

        categories.setText(
                business_info.get("category") + "-" +
                        business_info.get("subcategory")
        );

        String currDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" +
                (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" +
                Calendar.getInstance().get(Calendar.YEAR);

        today_appointments.setText(
                "" + server.count_appointments_on_date(username.getText().toString(), currDate, false)
        );


    }
}