package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.UserRegisterValidator;

public class RegisterActivity extends AppCompatActivity {
    // variables for the fields
    private EditText username, first_name, last_name, password, email, city, street, home_num, floor, birth_date;
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
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        home_num = findViewById(R.id.home_number);
        floor = findViewById(R.id.floor_number);
        birth_date = findViewById(R.id.birth_date_field);
        register_button = findViewById(R.id.register_button);

        // set event to button click
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
                //TODO add register functionalities and validation
                UserRegisterValidator parser = new UserRegisterValidator(username.getText().toString(), password.getText().toString(), first_name.getText().toString(), last_name.getText().toString(), email.getText().toString(), birth_date.getText().toString(), city.getText().toString(), street.getText().toString(), home_num.getText().toString(), floor.getText().toString());

                // change to next page if successful
                if (parser.is_valid()) {
                    Toast.makeText(RegisterActivity.this, "Registered Successful!ly", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
                    String first_name_str = first_name.getText().toString();
                    i.putExtra("first_name", first_name_str);
                    startActivity(i);
                } else {
                    Toast.makeText(RegisterActivity.this, "Register Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
