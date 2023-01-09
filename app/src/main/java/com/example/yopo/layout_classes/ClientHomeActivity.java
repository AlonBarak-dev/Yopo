package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.ServerFactory;
import com.example.yopo.data_classes.Session;
import com.example.yopo.interfaces.Server;

public class ClientHomeActivity extends AppCompatActivity {
    // field variables
    private Button search_button, calender_button, profile_button, logout_button;
    private Session session;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_home_layout);

        initializeServerAndSession();
        initializeFields();
        initializeButtonsOnClickEvents();
    }

    private void initializeServerAndSession() {
        try {
            server = ServerFactory.getServer("firestore");
        } catch (ClassNotFoundException e) {
            Log.e("ServerFactory", e.toString());
        }

        session = Session.getInstance();
        String username = getIntent().getStringExtra("username");
        session.add_session_attribute("username", username);
    }

    private void initializeFields() {
        search_button = findViewById(R.id.search);
        calender_button = findViewById(R.id.calender);
        profile_button = findViewById(R.id.profile);
        logout_button = findViewById(R.id.logout);
    }

    private void initializeButtonsOnClickEvents() {
        // set event listeners to the buttons
        // set event to button click
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHomeActivity.this, ClientSearchActivity.class));
            }
        });

        calender_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ClientHomeActivity.this, "Loading Calendar...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ClientHomeActivity.this, ClientCalendarActivity.class);
                startActivity(i);
            }
        });

        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ClientHomeActivity.this, "Loading Profile...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ClientHomeActivity.this, ClientProfileActivity.class);
                startActivity(i);
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ClientHomeActivity.this, "Logging Out...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ClientHomeActivity.this, MainActivity.class);
                // clear session when logging out
                session.clear_session();
                startActivity(i);
            }
        });
    }
}