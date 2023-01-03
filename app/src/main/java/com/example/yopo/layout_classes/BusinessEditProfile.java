package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yopo.R;
import com.example.yopo.data_classes.Server;
import com.example.yopo.data_classes.Session;

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

        server = Server.getInstance();
        session = Session.getInstance();

        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        home_num = findViewById(R.id.home_number);
        floor = findViewById(R.id.floor_number);
        phone_number = findViewById(R.id.phone_number);
        business_description = findViewById(R.id.business_description);
        business_name = findViewById(R.id.business_update_name);
        update = findViewById(R.id.update_business);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> business_data = new HashMap<>();

                if (!business_name.getText().toString().isEmpty()) {
                    business_data.put("business_name", business_name.getText().toString());
                }
                if (server.validate_email(email.getText().toString())) {
                    business_data.put("email", email.getText().toString());
                } else {
                    Toast.makeText(BusinessEditProfile.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                    return;
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

                if (server.update_document("business", session.get_session_attribute("username").toString(), business_data)) {
                    Toast.makeText(BusinessEditProfile.this, "Update successful!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(BusinessEditProfile.this, BusinessHomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(BusinessEditProfile.this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}