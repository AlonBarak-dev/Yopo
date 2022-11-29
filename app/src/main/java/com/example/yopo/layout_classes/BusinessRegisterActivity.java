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

import java.util.HashMap;

public class BusinessRegisterActivity extends AppCompatActivity {

    private EditText username, password, email, city, street, home_num, floor, phone_number, business_description;
    private Spinner categories, sub_categories;
    private Button register_button;
    private Database database;

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


        database = Database.getInstance();


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
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BusinessRegisterActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
                BusinessRegisterValidator parser = new BusinessRegisterValidator(username.getText().toString(), password.getText().toString(), email.getText().toString(), city.getText().toString(), street.getText().toString(), home_num.getText().toString(), floor.getText().toString(), phone_number.getText().toString());

                // change to next page if successful
                if (parser.is_valid()) {

                    // Create a new Hashmap for the new business user
                    HashMap<String, Object> business_data = new HashMap<>();
                    business_data.put("username", username.getText().toString());
                    business_data.put("password", password.getText().toString());
                    business_data.put("email", email.getText().toString());
                    business_data.put("city", city.getText().toString());
                    business_data.put("street", street.getText().toString());
                    business_data.put("home_num", home_num.getText().toString());
                    business_data.put("floor", floor.getText().toString());
                    business_data.put("phone", phone_number.getText().toString());

                    // add to the database
                    boolean success = database.add_new_business(business_data);
                    Log.d("ClientReg", "Success Status: " + success);

                    if (success){
                        Toast.makeText(BusinessRegisterActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(BusinessRegisterActivity.this, BusinessHomeActivity.class);
                        i.putExtra("username", username.getText().toString());
//                    String first_name_str = first_name.getText().toString();
//                    i.putExtra("first_name", first_name_str);
                        startActivity(i);
                    }
                    else {
                        Log.w("ClientReg", "Registration Failed");
                        Toast.makeText(BusinessRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(BusinessRegisterActivity.this, "Register Failed due to invalid inputs!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}