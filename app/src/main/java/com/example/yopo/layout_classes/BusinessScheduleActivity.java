package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.yopo.R;

public class BusinessScheduleActivity extends AppCompatActivity {
    // field variables
    private Spinner day_chooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_schedule);

        // get field variables
        day_chooser = findViewById(R.id.day_chooser);

        // create an array adapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_the_week, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        day_chooser.setAdapter(adapter);

    }
}