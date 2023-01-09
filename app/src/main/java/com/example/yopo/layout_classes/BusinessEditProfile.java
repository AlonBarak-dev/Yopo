package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.ServerFactory;
import com.example.yopo.data_classes.Session;
import com.example.yopo.interfaces.Server;

import java.util.HashMap;

public class BusinessEditProfile extends AppCompatActivity {

    private EditText email, city, street, home_num, floor, phone_number, business_description, business_name;
    private Session session;
    private Server server;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_edit_profile_layout);

        initializeServerAndSession();
        initializeFields();
        initializeButtonOnClickListener();
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
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        home_num = findViewById(R.id.home_number);
        floor = findViewById(R.id.floor_number);
        phone_number = findViewById(R.id.phone_number);
        business_description = findViewById(R.id.business_description);
        business_name = findViewById(R.id.business_update_name);
        update = findViewById(R.id.update_business);
    }

    private void initializeButtonOnClickListener() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HashMap<String, Object> business_data = getUpdatedBusinessData();
                    goToNextPage(business_data);
                } catch (IllegalArgumentException e) {
                    Log.e("EditProfile", e.toString());
                }
            }
        });
    }

    private void goToNextPage(HashMap<String, Object> business_data) {
        if (business_data.size() == 0) {
            goToBusinessProfile("No fields to update!");
        } else if (server.update_document("business", session.get_session_attribute("username").toString(), business_data)) {
            goToBusinessProfile("Update successful!");
        } else {
            Toast.makeText(BusinessEditProfile.this, "Update failed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToBusinessProfile(String message) {
        Toast.makeText(BusinessEditProfile.this, message, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(BusinessEditProfile.this, BusinessProfileActivity.class);
        startActivity(i);
        finish();
    }

    private HashMap<String, Object> getUpdatedBusinessData() throws IllegalArgumentException {
        HashMap<String, Object> business_data = new HashMap<>();

        if (!business_name.getText().toString().isEmpty()) {
            business_data.put("business_name", business_name.getText().toString());
        }
        if (server.validate_email(email.getText().toString())) {
            business_data.put("email", email.getText().toString());
        } else {
            if (!email.getText().toString().isEmpty()) {
                Toast.makeText(BusinessEditProfile.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                throw new IllegalArgumentException("Invalid Email!");
            }
        }
        if (!city.getText().toString().isEmpty()) {
            business_data.put("city", city.getText().toString());
        }
        if (!street.getText().toString().isEmpty()) {
            business_data.put("street", street.getText().toString());
        }
        if (!home_num.getText().toString().isEmpty()) {
            business_data.put("home_num", home_num.getText().toString());
        }
        if (!floor.getText().toString().isEmpty()) {
            business_data.put("floor", floor.getText().toString());
        }
        if (!phone_number.getText().toString().isEmpty()) {
            business_data.put("phone", phone_number.getText().toString());
        }
        if (!business_description.getText().toString().isEmpty()) {
            business_data.put("description", business_description.getText().toString());
        }

        return business_data;
    }
}