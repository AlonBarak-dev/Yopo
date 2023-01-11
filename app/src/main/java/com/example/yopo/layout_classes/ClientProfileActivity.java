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

public class ClientProfileActivity extends AppCompatActivity {

    private Session session;
    private Server server;

    private TextView name, username, email, client_address, today_appointments, phone_number;

    private Button edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_profile_layout);

        initializeServerAndSession();
        initializeFields();
        setFieldsValues();
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
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        client_address = findViewById(R.id.client_address);
        today_appointments = findViewById(R.id.today_appointments_num);
        edit_profile = findViewById(R.id.edit_profile);
    }

    private void setFieldsValues() {
        // get client document
        HashMap<String, Object> client_info = server.get_client_info(session.get_session_attribute("username").toString());

        // show business details in layout
        username.setText(client_info.get("username").toString());

        String name_String = client_info.get("first_name").toString() + " " + client_info.get("last_name").toString();
        name.setText(name_String);

        email.setText(client_info.get("email").toString());

        phone_number.setText(client_info.get("phone_number").toString());

        String client_address_string = client_info.get("city").toString() + "-" + client_info.get("street").toString() + "-" + client_info.get("home_num").toString() + "-" + client_info.get("floor").toString();
        client_address.setText(client_address_string);

        String currDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR);
        String today_appointments_string = "" + server.count_appointments_on_date(username.getText().toString(), currDate, true);
        today_appointments.setText(today_appointments_string);
    }

    private void initializeButtonOnClickEvent() {
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClientProfileActivity.this, ClientEditProfile.class);
                startActivity(i);
            }
        });
    }
}