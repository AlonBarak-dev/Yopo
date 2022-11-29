package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;

import java.util.HashMap;

public class BusinessLandingPageActivity extends AppCompatActivity {
    private Database database;

    private TextView business_name, business_description;

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

        // set fields to correct information
        // TODO use business name instead
        business_name.setText((String) business_data.get("username"));
        business_description.setText((String) business_data.get("description"));
    }
}