package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.ServerFactory;
import com.example.yopo.data_classes.Session;
import com.example.yopo.interfaces.Server;

import java.util.Calendar;
import java.util.HashMap;

public class BusinessProfileActivity extends AppCompatActivity {

    private Session session;
    private Server server;

    private TextView username, email, business_address, categories, today_appointments, phone_number;
    private Button edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_profile_layout);

        initializeServerAndSession();
        initializeFields();
        setFieldValues();
        initializeButtonOnClickEvent();
    }

    private void initializeServerAndSession() {
        try {
            server = ServerFactory.getServer("firestore");
        } catch (ClassNotFoundException e) {
            Log.e("ServerFactory", e.toString());
        }
        session = Session.getInstance();
    }

    private void initializeFields() {
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        business_address = findViewById(R.id.business_address);
        phone_number = findViewById(R.id.phone_number);
        categories = findViewById(R.id.category);
        today_appointments = findViewById(R.id.today_appointments_num);
        edit_profile = findViewById(R.id.edit_profile);
    }

    private void setFieldValues() {
        // get business document
        HashMap<String, Object> business_info = server.get_business_info(session.get_session_attribute("username").toString());

        // show business details in layout
        username.setText(
                business_info.get("username").toString()
        );

        email.setText(
                business_info.get("email").toString()
        );

        String business_address_string = business_info.get("city").toString() + "-" +
                business_info.get("street").toString() + "-" +
                business_info.get("home_num").toString() + "-" +
                business_info.get("floor").toString();
        business_address.setText(business_address_string);

        phone_number.setText(
                business_info.get("phone").toString()
        );

        String categories_string = business_info.get("category") + "-" +
                business_info.get("subcategory");
        categories.setText(categories_string);

        String currDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" +
                (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" +
                Calendar.getInstance().get(Calendar.YEAR);

        today_appointments.setText(
                "" + server.count_appointments_on_date(username.getText().toString(), currDate, false)
        );
    }

    private void initializeButtonOnClickEvent() {
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessProfileActivity.this, BusinessEditProfile.class);
                startActivity(i);
            }
        });

    }
}