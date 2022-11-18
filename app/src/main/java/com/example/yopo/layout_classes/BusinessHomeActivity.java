package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;

public class BusinessHomeActivity extends AppCompatActivity {

    private ImageButton logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_home_layout);

        logout = findViewById(R.id.logout_button_business);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logout from the profile -> back to Main page
                Intent i = new Intent(BusinessHomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
