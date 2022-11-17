package com.example.yopo.layout_classes;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.LoginValidation;


public class LoginActivity extends AppCompatActivity{

    // variables for the fields
    private EditText username, password;
    private Button login_button;
    private CheckBox business_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        // extract fields from the app
        username = findViewById(R.id.login_layout_username);
        password = findViewById(R.id.login_layout_password);
        login_button = findViewById(R.id.login_button);
        business_box = findViewById(R.id.login_checkBox);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Login...", Toast.LENGTH_SHORT).show();
                //TODO add register functionalities and validation
                LoginValidation parser = new LoginValidation(username.getText().toString(), password.getText().toString());
                // TODO add login functionalities and use validation from database
                if (parser.is_valid()){
                    // the input values are good
                    Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();

                    if (business_box.isChecked()){
                        // Business profile - TODO validate using firebase
                        Intent i = new Intent(LoginActivity.this, BusinessHome.class);
                        startActivity(i);
                    }
                    else{
                        // User profile - TODO validate using firebase
                        Intent i = new Intent(LoginActivity.this, ClientHomeActivity.class);
                        startActivity(i);
                    }

                } else {
                    Toast.makeText(LoginActivity.this, parser.get_error_string(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
