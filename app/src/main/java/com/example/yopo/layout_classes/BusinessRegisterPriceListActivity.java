package com.example.yopo.layout_classes;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.BusinessRegisterValidator;
import com.example.yopo.data_classes.ServerFactory;
import com.example.yopo.data_classes.Session;
import com.example.yopo.interfaces.Server;

import java.util.HashMap;

public class BusinessRegisterPriceListActivity extends AppCompatActivity {
    // TODO add price list attributes


    private Button register_button;
    private Session session;
    private Server server;

    private TextView service1, price1, service2, price2, service3, price3, service4, price4, service5, price5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_register_price_list_layout);

        initializeServerAndSession();
        initializeFields();
        initializeButtonOnClickEvent();
    }

    private void initializeServerAndSession() {
        try {
            server = ServerFactory.getServer("firestore");
        } catch (ClassNotFoundException e) {
            Log.e("ServerFactory", e.toString());
        }
        session = Session.getInstance();
    }

    private void initializeFields() {
        service1 = findViewById(R.id.service1);
        service2 = findViewById(R.id.service2);
        service3 = findViewById(R.id.service3);
        service4 = findViewById(R.id.service4);
        service5 = findViewById(R.id.service5);
        price1 = findViewById(R.id.service1price);
        price2 = findViewById(R.id.service2price);
        price3 = findViewById(R.id.service3price);
        price4 = findViewById(R.id.service4price);
        price5 = findViewById(R.id.service5price);
        register_button = findViewById(R.id.business_register_button);
    }

    private void initializeButtonOnClickEvent() {
        // extract the data from the previous stage.
        HashMap<String, Object> business_data = (HashMap<String, Object>) session.get_session_attribute("first_step_register_data");
        BusinessRegisterValidator parser = (BusinessRegisterValidator) session.get_session_attribute("business_register_parser");

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean is_valid = validateServiceRegistration(parser);

                String[] services = {service1.getText().toString(), service2.getText().toString(),
                        service3.getText().toString(), service4.getText().toString(), service5.getText().toString()};

                String[] prices = {price1.getText().toString(), price2.getText().toString(),
                        price3.getText().toString(), price4.getText().toString(), price5.getText().toString()};

                boolean business_added = false;
                boolean services_added = true;

                // validate user input and add to the database
                if (is_valid) {
                    // add to the database
                    business_added = server.add_new_business(business_data);
                    Log.d("ClientReg", "Success Status: " + business_added);
                    services_added = insertServicesToDataBase(business_data, services, prices);
                }
                // in case the addition went through move to the business home page, else print error message
                if (business_added && services_added) {
                    goToBusinessHomePage(business_data);
                } else {
                    Log.w("ClientReg", "Registration Failed");
                    Toast.makeText(BusinessRegisterPriceListActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean validateServiceRegistration(BusinessRegisterValidator parser) {
        return parser.price_list_is_valid(service1.getText().toString(), price1.getText().toString(),
                service2.getText().toString(), price2.getText().toString(),
                service3.getText().toString(), price3.getText().toString(),
                service4.getText().toString(), price4.getText().toString(),
                service5.getText().toString(), price5.getText().toString());
    }

    private Boolean insertServicesToDataBase(HashMap<String, Object> business_data, String[] services, String[] prices) {
        int MAX_SERVICES = 5;
        HashMap<String, Object> service;
        for (int i = 0; i < MAX_SERVICES; i++) {
            // save each service with its price and business username
            service = new HashMap<>();
            service.put("username", business_data.get("username"));
            service.put("service", services[i]);
            service.put("price", prices[i]);
            service.put("service_id", business_data.get("username") + "-" + services[i]);
            if (((String) service.get("username")).isEmpty() || ((String) service.get("price")).isEmpty()) {
                continue;
            }
            if (server.add_new_service(service) == false) { // add service to database
                return false;

            }
        }

        return true;
    }

    private void goToBusinessHomePage(HashMap<String, Object> business_data) {
        Toast.makeText(BusinessRegisterPriceListActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(BusinessRegisterPriceListActivity.this, BusinessHomeActivity.class);
        i.putExtra("username", (String) business_data.get("username"));
        startActivity(i);
        finish();
    }
}