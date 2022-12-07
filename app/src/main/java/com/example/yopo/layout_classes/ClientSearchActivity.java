package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yopo.R;
import com.example.yopo.data_classes.ClientRegisterValidator;
import com.example.yopo.data_classes.Database;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class ClientSearchActivity extends AppCompatActivity {
    private TextInputLayout search_bar;
    private ImageButton search_button;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_search_layout);

        // get database
        database = Database.getInstance();

        // get the field variables
        search_button = findViewById(R.id.search_button);
        search_bar = findViewById(R.id.search_bar);

        // set event to button click
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ClientSearchActivity.this, "Searching...", Toast.LENGTH_SHORT).show();

                // get business by the given name
                // TODO use business name instead of username
                String search_query_name = search_bar.getEditText().getText().toString();
                HashMap<String, Object> business_info = database.get_business_info_by_name(search_query_name);

                // validate
                if (business_info != null) {
                    Intent i = new Intent(ClientSearchActivity.this, BusinessLandingPageActivity.class);
                    Toast.makeText(ClientSearchActivity.this, "Business Found!", Toast.LENGTH_SHORT).show();
                    i.putExtra("business_username", (String) business_info.get("username"));
                    startActivity(i);
                }
                else {
                    Toast.makeText(ClientSearchActivity.this, "Business Not Found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}