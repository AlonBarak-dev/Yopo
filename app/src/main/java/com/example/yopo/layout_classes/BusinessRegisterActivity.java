package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yopo.R;
import com.example.yopo.data_classes.BusinessRegisterValidator;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Server;
import com.example.yopo.data_classes.Session;

import java.util.HashMap;

public class BusinessRegisterActivity extends AppCompatActivity {

    private EditText username, password, email, city, street, home_num, floor, phone_number, business_description, business_name;
    private Spinner categories, sub_categories;
    private Button next_button;
    private Database database;
    private Session session;
    private Server server;

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
        categories = findViewById(R.id.avaliable_hours_spinner);
        sub_categories = findViewById(R.id.sub_categories);
        next_button = findViewById(R.id.next_register_business);
        business_description = findViewById(R.id.business_description);
        business_name = findViewById(R.id.business_register_name);


        database = Database.getInstance();
        server = Server.getInstance();
        session = Session.getInstance();


        // Create a static (XML) ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array,
                android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categories.setAdapter(staticAdapter);

        // Create dynamic ArrayAdapters for each category
        String[] defaults = new String[]{"Choose Category"};
        String[] foods = new String[]{"Market", "Restaurant", "Grocery"};
        String[] electronics = new String[]{"Repair Lab", "Retailer"};
        String[] sports = new String[]{"Sports wears", "Equipment"};
        String[] other = new String[]{"Barber", "Cosmetics", "Handyman", "Private Teacher"};

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
                switch (i) {
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
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BusinessRegisterActivity.this, "Loading...", Toast.LENGTH_SHORT).show();
                BusinessRegisterValidator parser = new BusinessRegisterValidator(username.getText().toString(), password.getText().toString(), email.getText().toString(), city.getText().toString(), street.getText().toString(), home_num.getText().toString(), floor.getText().toString(), phone_number.getText().toString(), business_name.getText().toString());

                // change to next page if successful
                if (parser.is_valid()) {


                    // Create a new Hashmap for the new business user
                    HashMap<String, Object> business_data = new HashMap<>();
                    business_data.put("business_name", business_name.getText().toString());
                    business_data.put("username", username.getText().toString());
                    business_data.put("password", password.getText().toString());
                    business_data.put("email", email.getText().toString());
                    business_data.put("city", city.getText().toString());
                    business_data.put("street", street.getText().toString());
                    business_data.put("home_num", home_num.getText().toString());
                    business_data.put("floor", floor.getText().toString());
                    business_data.put("phone", phone_number.getText().toString());
                    business_data.put("description", business_description.getText().toString());
                    business_data.put("category", categories.getSelectedItem().toString());
                    business_data.put("subcategory", sub_categories.getSelectedItem().toString());

                    // add to session
                    session.add_session_attribute("first_step_register_data", business_data);
                    session.add_session_attribute("business_register_parser", parser);

                    Intent i = new Intent(BusinessRegisterActivity.this, BusinessRegisterPriceListActivity.class);
                    startActivity(i);

                } else {
                    Toast.makeText(BusinessRegisterActivity.this, "This step Failed due to invalid inputs!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}