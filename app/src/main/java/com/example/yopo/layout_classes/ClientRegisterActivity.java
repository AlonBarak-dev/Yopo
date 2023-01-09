package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.ClientRegisterValidator;
import com.example.yopo.data_classes.FirebaseServer;

import java.util.HashMap;

public class ClientRegisterActivity extends AppCompatActivity {
    // variables for the fields
    private EditText username, first_name, last_name, password, email, city, street, home_num, floor, birth_date, phone_number;
    private Button register_button;
    private FirebaseServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_register_layout);

        initializeServer();
        initializeFields();
        initializeButtonOnClickEvent();
    }

    private void initializeFields() {
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
        phone_number = findViewById(R.id.phone_number);
        register_button = findViewById(R.id.next_register_business);
    }

    private void initializeServer() {
        server = FirebaseServer.getInstance();
    }

    private void initializeButtonOnClickEvent() {
        // set event to button click
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ClientRegisterActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
                ClientRegisterValidator parser = new ClientRegisterValidator(username.getText().toString(), password.getText().toString(), first_name.getText().toString(), last_name.getText().toString(), email.getText().toString(), birth_date.getText().toString(), city.getText().toString(), street.getText().toString(), home_num.getText().toString(), floor.getText().toString(), phone_number.getText().toString());

                if (parser.is_valid()) {
                    HashMap<String, Object> client_data = constructClientDataMap();

                    boolean success = server.add_new_client(client_data);
                    Log.d("ClientReg", "Success Status: " + success);

                    if (success) {
                        goToHomePage();
                    } else {
                        logError();
                    }
                } else {
                    Toast.makeText(ClientRegisterActivity.this, "Register failed due to invalid inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private HashMap<String, Object> constructClientDataMap() {
        HashMap<String, Object> client_data = new HashMap<>();
        client_data.put("username", username.getText().toString());
        client_data.put("first_name", first_name.getText().toString());
        client_data.put("last_name", last_name.getText().toString());
        client_data.put("password", password.getText().toString());
        client_data.put("email", email.getText().toString());
        client_data.put("city", city.getText().toString());
        client_data.put("street", street.getText().toString());
        client_data.put("home_num", home_num.getText().toString());
        client_data.put("floor", floor.getText().toString());
        client_data.put("birthdate", birth_date.getText().toString());
        client_data.put("phone_number", phone_number.getText().toString());

        return client_data;
    }

    private void goToHomePage() {
        Toast.makeText(ClientRegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ClientRegisterActivity.this, ClientHomeActivity.class);
        i.putExtra("username", username.getText().toString());
        startActivity(i);
        finish();
    }

    private void logError() {
        Log.w("ClientReg", "Registration Failed");
        Toast.makeText(ClientRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
    }
}
