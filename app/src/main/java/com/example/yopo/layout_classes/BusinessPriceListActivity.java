package com.example.yopo.layout_classes;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.yopo.R;
import com.example.yopo.data_classes.BusinessRegisterValidator;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Session;
import java.util.HashMap;

public class BusinessPriceListActivity extends AppCompatActivity {
    // TODO add price list attributes


    private Button register_button;
    private Session session;
    private Database database;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.price_list_layout);

        register_button = findViewById(R.id.business_register_button);
        session = Session.getInstance();
        database = Database.getInstance();


        HashMap<String, Object> business_data = (HashMap<String, Object>) session.get_session_attribute("first_step_register_data");



        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO add price list validation and save to database

//                 add to the database
                boolean success = database.add_new_business(business_data);
                Log.d("ClientReg", "Success Status: " + success);

                if (success) {
                    Toast.makeText(BusinessPriceListActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(BusinessPriceListActivity.this, BusinessHomeActivity.class);
                    i.putExtra("username", (String)business_data.get("username"));
                    startActivity(i);
                } else {
                    Log.w("ClientReg", "Registration Failed");
                    Toast.makeText(BusinessPriceListActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }





            }
        });






    }
}