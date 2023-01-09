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

import java.util.HashMap;

public class BusinessLandingPageActivity extends AppCompatActivity {
    private Session session;
    private Server server;

    private TextView business_name, business_description;
    private Button schedule_button, services_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_landing_page_layout);

        initializeServerAndSession();

        // get data from database about business
        String business_username = getIntent().getStringExtra("business_username");
        session.add_session_attribute("business_username", business_username);
        HashMap<String, Object> business_data = server.get_business_info(business_username);

        initializeFields(business_data);

        initializeButtonsOnClickEvents();
    }

    private void initializeButtonsOnClickEvents() {
        schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BusinessLandingPageActivity.this, ClientAppointmentScheduleActivity.class);
                startActivity(i);
            }
        });

        services_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BusinessLandingPageActivity.this, BusinessPriceListActivity.class);
                startActivity(i);
            }
        });
    }

    private void initializeServerAndSession() {
        try {
            server = ServerFactory.getServer("firestore");
        } catch (ClassNotFoundException e) {
            Log.e("ServerType", e.toString());
        }
        session = Session.getInstance();
    }

    private void initializeFields(HashMap<String, Object> data) {
        business_name = findViewById(R.id.business_name);
        business_description = findViewById(R.id.business_description_text);
        schedule_button = findViewById(R.id.schedule_button);
        services_button = findViewById(R.id.services_button);

        business_name.setText((String) data.get("business_name"));
        business_description.setText((String) data.get("description"));
    }
}