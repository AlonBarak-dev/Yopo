package com.example.yopo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.yopo.data_classes.ClientRegisterValidator;
import com.example.yopo.layout_classes.BusinessHomeActivity;

public class BusinessRegisterActivity extends AppCompatActivity {

    private EditText username, password, email, city, street, home_num, floor, phone_number, business_description;
    private Spinner categories, sub_categories;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_register_layout);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        home_num = findViewById(R.id.home_number);
        floor = findViewById(R.id.floor_number);
        phone_number = findViewById(R.id.phone_number);
        categories = findViewById(R.id.categories);
        sub_categories = findViewById(R.id.sub_categories);
        register_button = findViewById(R.id.register_button);


        // Create a static (XML) ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this,
                                                                                    R.array.category_array,
                                                                                    android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categories.setAdapter(staticAdapter);

        // Create dynamic ArrayAdapters for each category
        String[] defaults = new String[] {"Choose Category"};
        String[] foods = new String[] {"Market", "Restaurant", "Grocery"};
        String[] electronics = new String[] {"Repair Lab", "Retailer"};
        String[] sports = new String[] {"Sports wears", "Equipment"};
        String[] other = new String[] {"Barber", "Cosmetics", "Handyman", "Private Teacher"};

        ArrayAdapter<String> default_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, defaults);
        ArrayAdapter<String> foods_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, foods);
        ArrayAdapter<String> electronics_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, electronics);
        ArrayAdapter<String> sports_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sports);
        ArrayAdapter<String> other_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, other);

        sub_categories.setAdapter(default_adapter);

        // correspond categories with their sub-categories
        categories.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i) {
                    case 1:
                        sub_categories.setAdapter(foods_adapter);
                        break;
                    case 2:
                        sub_categories.setAdapter(electronics_adapter);
                        break;
                    case 3:
                        sub_categories.setAdapter(sports_adapter);
                        break;
                    case 4:
                        sub_categories.setAdapter(other_adapter);
                        break;
                    default:
                        sub_categories.setAdapter(default_adapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            // TODO implement input validation
            @Override
            public void onClick(View v) {
                Toast.makeText(BusinessRegisterActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
                Toast.makeText(BusinessRegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(BusinessRegisterActivity.this, BusinessHomeActivity.class);
                startActivity(i);
            }
        });

    }
}