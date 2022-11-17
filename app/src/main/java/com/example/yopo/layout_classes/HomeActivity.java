package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yopo.R;
import com.example.yopo.data_classes.UserRegisterValidator;

public class HomeActivity extends AppCompatActivity {
    // field variables
    private Button search_button, calender_button, profile_button, logout_button;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

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
                startActivity(new Intent(HomeActivity.this, UserSearchActivity.class));
            }
        });
    }
}