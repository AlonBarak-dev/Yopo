package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yopo.R;

public class BusinessScheduleActivity extends AppCompatActivity {
    // field variables
    private Spinner day_chooser;
    private ConstraintLayout day_viewer;
    private TextView day_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_schedule);

        // get field variables
        day_chooser = findViewById(R.id.day_chooser);
        day_viewer = findViewById(R.id.day_viewer);
        day_text = day_viewer.findViewById(R.id.day_text);

        // create an array adapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_the_week, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        day_chooser.setAdapter(adapter);

        // set event listeners
        day_chooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = "Schedule for " + adapter.getItem(position).toString();
                day_text.setText(text);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}