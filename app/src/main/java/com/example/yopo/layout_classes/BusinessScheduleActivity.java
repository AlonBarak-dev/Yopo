package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Session;
import com.example.yopo.util_classes.OpenRange;
import com.example.yopo.util_classes.OpenRangeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BusinessScheduleActivity extends AppCompatActivity {
    //data variables
    private Database database;
    private Session session;

    // field variables
    private Spinner day_chooser;
    private RecyclerView ranges;
    private Button edit_services, apply_schedule, new_range;
    private ArrayList<LinkedList<OpenRange>> ranges_per_day;
    private OpenRangeAdapter range_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_schedule_layout);

        // Get database and session singletons
        database = Database.getInstance();
        session = Session.getInstance();

        // get field variables
        day_chooser = findViewById(R.id.day_chooser);
        edit_services = findViewById(R.id.edit_services_button);
        apply_schedule = findViewById(R.id.apply_schedule);
        ranges = findViewById(R.id.ranges);
        new_range = findViewById(R.id.new_range);

        // create an array adapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days_of_the_week, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        day_chooser.setAdapter(adapter);

        // Init list of the open ranges for each day
        ranges_per_day = new ArrayList<LinkedList<OpenRange>>();
        for (int i = 0; i < 7; ++i) {
            // Create list for each day
            ranges_per_day.add(new LinkedList<>());

            // Get data from database about this day
            String key = (String) session.get_session_attribute("username") + "-" + (String) day_chooser.getItemAtPosition(i);
            HashMap<String, Object> data = database.get_open_range_by_day((String) session.get_session_attribute("username"), (String) day_chooser.getItemAtPosition(i));

            // update list from database, only if there is actuall data to work with
            if (data != null) {
                update_list(ranges_per_day.get(i), data);
            }
        }

        // Set on selection event listener for the "day_chooser" spinner
        day_chooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Setup recycler view when choosing a day
                List<OpenRange> range_list = ranges_per_day.get(position);
                range_adapter = new OpenRangeAdapter(BusinessScheduleActivity.this, range_list);

                // Create layout manager
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BusinessScheduleActivity.this, LinearLayoutManager.VERTICAL, false);

                ranges.setLayoutManager(linearLayoutManager);
                ranges.setAdapter(range_adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // Set click event listener on the "new_range" button
        new_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get selected item position in the spinner
                // this means we get the index of the chosen day
                int day_position = BusinessScheduleActivity.this.day_chooser.getSelectedItemPosition();

                // Add a new range to the list corresponding to the
                // chosen day
                BusinessScheduleActivity.this.ranges_per_day.get(day_position).add(new OpenRange(0, 0));

                // Update recycler view data
                range_adapter.notifyItemInserted(ranges_per_day.get(day_position).size() - 1);
            }
        });

        // Set click event listener on the "apply_schedule" button
        apply_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create dictionary, where the keys are hours and the values are
                // booleans indicating if the business is oen at that hour or not
                HashMap<String, HashMap<String, Object>> day_map = new HashMap<>();
                for (int i = 0; i < ranges_per_day.size(); ++i) {
                    // Skip on days if no range was defined in it
                    if (ranges_per_day.get(i).isEmpty()) {
                        continue;
                    }

                    // This will be used as the key of the hour_map for the day
                    // and also the key in the database
                    String document_name = ((String) session.get_session_attribute("username")) + "-" + day_chooser.getItemAtPosition(i);

                    // Map the hours with the corresponding gloat values
                    HashMap<String, Object> hour_map = new HashMap<>();
                    for (int j = 0; j < 24; ++j) {
                        hour_map.put(index_to_hour(j), false);
                    }

                    // Update ranges
                    update_hour_map(hour_map, i);

                    // Add map to "day_map"
                    day_map.put(document_name, hour_map);
                }


                // Update dictionary in database
                for (String key : day_map.keySet()) {
                    Log.d("HourMapKey", "Key: " + key);
                    Log.d("HourMap", "Map: " + day_map.get(key));

                    // Add to database
                    database.add_to_collection(day_map.get(key), key, "business_open_times");
                }
            }
        });

        // move to edit services layout
        edit_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessScheduleActivity.this, BusinessEditServicesActivity.class);
                startActivity(i);
            }
        });


    }

    /**
     * Convert index to the corresponding hour
     *
     * @param index index value to convert
     * @return A String representing the hour
     */
    private static String index_to_hour(int index) {
        if (index < 10) {
            return "0" + index + "-00";
        }
        return index + "-00";
    }

    /**
     * Update the values in the hour map according to the saved ranges
     *
     * @param hour_map the map to update
     */
    private void update_hour_map(HashMap<String, Object> hour_map, int index) {
        for (OpenRange range : ranges_per_day.get(index)) {
            int size = range.getEnd_hour() - range.getStart_hour();
            for (int i = range.getStart_hour(); i < range.getEnd_hour(); ++i) {
                hour_map.put(index_to_hour(i), true);
            }
        }
    }

    /**
     * Put the proper OpenRange objects from the given hashmap
     *
     * @param rangeList the list of OpenRange instances
     * @param map       the map received from the database
     */
    private void update_list(List<OpenRange> rangeList, HashMap<String, Object> map) {
        // Search for the first index that has true as its value
        for (int i = 0; i < map.size(); ++i) {
            // Get the key of the current time we are looking at
            String key = index_to_hour(i);

            // If we get null, then we are done
            if (map.get(key) == null) {
                break;
            }
            // If we get false we can skip
            else if (!(Boolean) map.get(key)) {
                continue;
            }
            // If we get true, we want to look at the next places until we get a false again
            int start = i;
            while (i < map.size() && (Boolean) map.get(index_to_hour(i))) {
                i++;
            }
            int end = i;

            rangeList.add(new OpenRange(start, end));
        }

        Log.d("UpdateList", "Map: " + map);
    }

}