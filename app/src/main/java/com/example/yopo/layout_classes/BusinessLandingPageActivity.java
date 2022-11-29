package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;

import java.util.HashMap;

public class BusinessLandingPageActivity extends AppCompatActivity {
    private Database database;

    private TextView business_name, business_description;
    private Button schedule_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_landing_page_layout);

        // get database instance
        database = Database.getInstance();

        // get intent info
        String business_username = getIntent().getStringExtra("business_username");

        // get data from database about business
        HashMap<String, Object> business_data = database.get_business_info(business_username);

        // get field variables
        business_name = findViewById(R.id.business_name);
        business_description = findViewById(R.id.business_description_text);
        schedule_button = findViewById(R.id.schedule_button);

        // set fields to correct information
        // TODO use business name instead
        business_name.setText((String) business_data.get("username"));
        business_description.setText((String) business_data.get("description"));

        // set event to button click
        schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BusinessLandingPageActivity.this, ClientAppointmentScheduleActivity.class);
                startActivity(i);
            }
        });

    }
}