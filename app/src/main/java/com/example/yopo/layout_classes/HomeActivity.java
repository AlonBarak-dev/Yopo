package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.yopo.R;

public class HomeActivity extends AppCompatActivity {
    // field variables
    private TextView welcome_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // get field variables
        welcome_text = findViewById(R.id.home_success);

        // get username from register page
        String first_name = getIntent().getStringExtra("first_name");
        String welcome_message = "Hello " + first_name + "!";
        welcome_text.setText(welcome_message);
    }
}