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

public class ClientEditProfile extends AppCompatActivity {

    private EditText first_name, last_name, email, city, street, home_num, floor, birth_date, phone_number;
    private Session session;
    private Server server;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_edit_profile_layout);

        server = Server.getInstance();
        session = Session.getInstance();

        first_name = findViewById(R.id.first_name);
        last_name  =findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        home_num = findViewById(R.id.home_number);
        floor = findViewById(R.id.floor_number);
        birth_date = findViewById(R.id.birth_date_field);
        phone_number = findViewById(R.id.phone_number);
        update = findViewById(R.id.update_client);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> client_data = new HashMap<>();

                if (!first_name.getText().toString().isEmpty()) {
                    client_data.put("first_name", first_name.getText().toString());
                }
                if (!last_name.getText().toString().isEmpty()) {
                    client_data.put("last_name", last_name.getText().toString());
                }
                if (server.validate_email(email.getText().toString())) {
                    client_data.put("email", email.getText().toString());
                } else {
                    if (!email.getText().toString().isEmpty()) {
                        Toast.makeText(ClientEditProfile.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (!city.getText().toString().isEmpty()) {
                    client_data.put("city", city.getText().toString());
                }
                if (!street.getText().toString().isEmpty()) {
                    client_data.put("street", street.getText().toString());
                }
                if (!home_num.getText().toString().isEmpty()) {
                    client_data.put("home_num", home_num.getText().toString());
                }
                if (!floor.getText().toString().isEmpty()) {
                    client_data.put("floor", floor.getText().toString());
                }
                if (!birth_date.getText().toString().isEmpty()) {
                    client_data.put("phone", birth_date.getText().toString());
                }
                if (!phone_number.getText().toString().isEmpty()) {
                    client_data.put("phone_number", phone_number.getText().toString());
                }

                if (client_data.size() == 0) {
                    Toast.makeText(ClientEditProfile.this, "No fields to update!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ClientEditProfile.this, ClientProfileActivity.class);
                    startActivity(i);
                    finish();
                } else if (server.update_document("clients", session.get_session_attribute("username").toString(), client_data)) {
                    Toast.makeText(ClientEditProfile.this, "Update successful!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ClientEditProfile.this, ClientProfileActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(ClientEditProfile.this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}