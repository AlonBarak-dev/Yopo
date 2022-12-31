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
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Server;

import java.util.HashMap;

public class ClientRegisterActivity extends AppCompatActivity {
    // variables for the fields
    private EditText username, first_name, last_name, password, email, city, street, home_num, floor, birth_date, phone_number;
    private Button register_button;
    private Database database;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_register_layout);
        // get database instance
        database = Database.getInstance();
        // get server instance
        server = Server.getInstance();

        // get field variables
        // text input
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

        // buttons
        register_button = findViewById(R.id.next_register_business);

        // set event to button click
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ClientRegisterActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
                ClientRegisterValidator parser = new ClientRegisterValidator(username.getText().toString(), password.getText().toString(), first_name.getText().toString(), last_name.getText().toString(), email.getText().toString(), birth_date.getText().toString(), city.getText().toString(), street.getText().toString(), home_num.getText().toString(), floor.getText().toString(), phone_number.getText().toString());

                // change to next page if successful
                if (parser.is_valid()) {
                    // register user
                    // create hashmap for new user
                    HashMap<String, Object> client_data = new HashMap<>();
                    client_data.put("username", username.getText().toString());
                    client_data.put("first_name", first_name.getText().toString());
                    client_data.put("last_name", last_name.getText().toString());
                    client_data.put("password", password.getText().toString());
                    client_data.put("email", email.getText().toString());
                    client_data.put("city", city.getText().toString());
                    client_data.put("street", street.getText().toString());
                    client_data.put("home_num", Integer.parseInt(home_num.getText().toString()));
                    client_data.put("floor", Integer.parseInt(floor.getText().toString()));
                    client_data.put("birthdate", birth_date.getText().toString());
                    client_data.put("phone_number", Integer.parseInt(phone_number.getText().toString()));


                    // add to the database
//                    boolean success = database.add_new_client(client_data);
//                    boolean success = true;
                    boolean success = server.add_new_client(client_data);
                    Log.d("ClientReg", "Success Status: " + success);

                    if (success) {
                        // go to home page
                        Toast.makeText(ClientRegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ClientRegisterActivity.this, ClientHomeActivity.class);
                        i.putExtra("username", username.getText().toString());
                        startActivity(i);
                        finish();
                    } else {
                        Log.w("ClientReg", "Registration Failed");
                        Toast.makeText(ClientRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ClientRegisterActivity.this, "Register failed due to invalid inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
