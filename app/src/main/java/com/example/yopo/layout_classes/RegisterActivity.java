package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;

public class RegisterActivity extends AppCompatActivity {
    // variables for the fields
    private EditText username, first_name, last_name, password;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register_layout);

        // get fields variables
        username = findViewById(R.id.username);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        password = findViewById(R.id.password);
        register_button = findViewById(R.id.register_button);

        // set event to button click
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
                //TODO add register functionalities and validation

                // change to next page if successful
                Toast.makeText(RegisterActivity.this, "Registered Successful!ly", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                String first_name_str = first_name.getText().toString();
                i.putExtra("first_name", first_name_str);
                startActivity(i);
            }
        });
    }
}
