package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Server;
import com.example.yopo.data_classes.Session;

import java.util.Calendar;
import java.util.HashMap;

public class ClientProfileActivity extends AppCompatActivity {

    private Database database;
    private Session session;
    private Server server;

    private TextView name, username, email, client_address, today_appointments, phone_number;

    private Button edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_profile_layout);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        client_address = findViewById(R.id.client_address);
        today_appointments = findViewById(R.id.today_appointments_num);
        edit_profile = findViewById(R.id.edit_profile);

        // extract database and session
        database = Database.getInstance();
        session = Session.getInstance();
        server = Server.getInstance();

        // get client document
        HashMap<String, Object> client_info = server.get_client_info(session.get_session_attribute("username").toString());

        // show business details in layout
        username.setText(
                client_info.get("username").toString()
        );

        name.setText(
                client_info.get("first_name").toString() + " " +
                        client_info.get("last_name").toString()
        );

        email.setText(
                client_info.get("email").toString()
        );

        phone_number.setText(
                client_info.get("phone_number").toString()
        );

        client_address.setText(
                client_info.get("city").toString() + "-" +
                        client_info.get("street").toString() + "-" +
                        client_info.get("home_num").toString() + "-" +
                        client_info.get("floor").toString()
        );

        String currDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" +
                (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" +
                Calendar.getInstance().get(Calendar.YEAR);

        today_appointments.setText(
                "" + server.count_appointments_on_date(username.getText().toString(), currDate, true)
        );

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ClientProfileActivity.this, ClientEditProfile.class);
                startActivity(i);
            }
        });
    }
}