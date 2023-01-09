package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;

public class RegisterActivity extends AppCompatActivity {

    private Button client_button;
    private Button business_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        initializeFields();
        initializeButtonOnClickEvents();
    }

    private void initializeFields() {
        client_button = findViewById(R.id.client_button);
        business_button = findViewById(R.id.business_button);
    }

    private void initializeButtonOnClickEvents() {
        client_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, ClientRegisterActivity.class);
                startActivity(i);
            }
        });

        business_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, BusinessRegisterActivity.class);
                startActivity(i);
            }
        });
    }
}