package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.LoginValidation;
import com.example.yopo.data_classes.ServerFactory;
import com.example.yopo.interfaces.Server;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {

    // variables for the fields
    private EditText username, password;
    private Button login_button;
    private CheckBox business_box;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        initializeServer();
        initializeFields();
        initializeButtonOnClickEvent();
    }

    private void initializeServer() {
        try {
            server = ServerFactory.getServer("firestore");
        } catch (ClassNotFoundException e) {
            Log.e("ServerFactory", e.toString());
        }
    }

    private void initializeFields() {
        username = findViewById(R.id.login_layout_username);
        password = findViewById(R.id.login_layout_password);
        login_button = findViewById(R.id.login_button);
        business_box = findViewById(R.id.login_checkBox);
    }

    private void initializeButtonOnClickEvent() {
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Login...", Toast.LENGTH_SHORT).show();
                LoginValidation parser = new LoginValidation(username.getText().toString(), password.getText().toString());
                if (parser.is_valid()) {
                    continueLogin();
                } else {
                    Toast.makeText(LoginActivity.this, parser.get_error_string(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToBusinessHomePage() {
        Intent i = new Intent(LoginActivity.this, BusinessHomeActivity.class);
        i.putExtra("username", username.getText().toString());
        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
        startActivity(i);
        finish();
    }

    private void goToClientHomePage() {
        Intent i = new Intent(LoginActivity.this, ClientHomeActivity.class);
        i.putExtra("username", username.getText().toString());
        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
        startActivity(i);
        finish();
    }

    private void continueLogin() {
        if (business_box.isChecked()) {
            loginAsBusiness();
        } else {
            loginAsClient();
        }
    }

    private void loginAsBusiness() {
        // make sure the user exists in the database -> username to password
        HashMap<String, Object> user = server.get_business_info(username.getText().toString());
        if (user != null && user.get("password").toString().equals(password.getText().toString())) {
            goToBusinessHomePage();
        } else {
            Toast.makeText(LoginActivity.this, "Username/Password is wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginAsClient() {
        HashMap<String, Object> user = server.get_client_info(username.getText().toString());
        if (user != null && user.get("password").toString().equals(password.getText().toString())) {
            goToClientHomePage();
        } else {
            Toast.makeText(LoginActivity.this, "Username/Password is wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}
