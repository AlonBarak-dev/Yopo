package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yopo.R;

public class ClientHomeActivity extends AppCompatActivity {
    // field variables
    private Button search_button, calender_button, profile_button, logout_button;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_home_layout);

        // get fields
        search_button = findViewById(R.id.search);
        calender_button = findViewById(R.id.calender);
        profile_button = findViewById(R.id.profile);
        logout_button = findViewById(R.id.logout);

        // set event listeners to the buttons
        // set event to button click
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClientHomeActivity.this, ClientSearchActivity.class));
            }
        });
    }
}