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
import com.example.yopo.data_classes.FirebaseServer;
import com.example.yopo.data_classes.Session;
import com.example.yopo.util_classes.OpenRange;
import com.example.yopo.util_classes.OpenRangeAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BusinessScheduleActivity extends AppCompatActivity {
    //data variables
    private Session session;
    private FirebaseServer server;

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

        initializeServerAndSession();
        initializeFields();
        loadSpinnerData();
        initializeAdapter();
        initializeSpinnerOnSelectedEvent();
        initializeButtonsOnClickEvents();
    }

    private void initializeServerAndSession() {
        server = FirebaseServer.getInstance();
        session = Session.getInstance();
    }

    private void initializeFields() {
        day_chooser = findViewById(R.id.day_chooser);
        edit_services = findViewById(R.id.edit_services_button);
        apply_schedule = findViewById(R.id.apply_schedule);
        ranges = findViewById(R.id.ranges);
        new_range = findViewById(R.id.new_range);
    }

    private void loadSpinnerData() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days_of_the_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day_chooser.setAdapter(adapter);
    }

    private void initializeAdapter() {
        int DAYS_IN_A_WEEK = 7;

        // Init list of the open ranges for each day
        ranges_per_day = new ArrayList<LinkedList<OpenRange>>();
        for (int i = 0; i < DAYS_IN_A_WEEK; ++i) {
            // Create list for each day
            ranges_per_day.add(new LinkedList<>());

            // Get data from database about this day
            Log.d("get_open_range_by_day", "INITIATED");
            HashMap<String, Object> data = server.get_open_range_by_day((String) session.get_session_attribute("username"), (String) day_chooser.getItemAtPosition(i));

            // update list from database, only if there is actual data to work with
            if (data != null) {
                update_list(ranges_per_day.get(i), data);
            }
        }

        ranges.setLayoutManager(null);
        ranges.setAdapter(null);
    }

    private void initializeButtonsOnClickEvents() {
        // Set click event listener on the "new_range" button
        new_range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day_position = BusinessScheduleActivity.this.day_chooser.getSelectedItemPosition();
                BusinessScheduleActivity.this.ranges_per_day.get(day_position).add(new OpenRange(0, 0));
                range_adapter.notifyItemInserted(ranges_per_day.get(day_position).size() - 1);
            }
        });

        // Set click event listener on the "apply_schedule" button
        apply_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, HashMap<String, Object>> day_map = new HashMap<>();
                for (int i = 0; i < ranges_per_day.size(); ++i) {
                    if (ranges_per_day.get(i).isEmpty()) {
                        continue;
                    }

                    insertHourMapToDayMap(day_map, i);
                }

                // Update dictionary in database
                for (String key : day_map.keySet()) {
                    server.add_to_collection(day_map.get(key), key, "business_open_times");
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

    private void initializeSpinnerOnSelectedEvent() {
        // Set on selection event listener for the "day_chooser" spinner
        day_chooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Setup recycler view when choosing a day
                List<OpenRange> range_list = ranges_per_day.get(position);
                range_adapter = new OpenRangeAdapter(BusinessScheduleActivity.this, range_list);

                // Create layout manager
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BusinessScheduleActivity.this, LinearLayoutManager.VERTICAL, false);

                Log.d("ranges", "setLayoutManager");
                ranges.setLayoutManager(linearLayoutManager);
                Log.d("ranges", "setAdapter");
                ranges.setAdapter(range_adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void insertHourMapToDayMap(HashMap<String, HashMap<String, Object>> day_map, int i) {
        int HOURS_IN_DAY = 24;
        String document_name = ((String) session.get_session_attribute("username")) + "-" + day_chooser.getItemAtPosition(i);

        // Map the hours with the corresponding boolean values
        HashMap<String, Object> hour_map = new HashMap<>();
        for (int j = 0; j < HOURS_IN_DAY; ++j) {
            hour_map.put(index_to_hour(j), false);
        }

        // Update ranges
        update_hour_map(hour_map, i);

        // Add map to "day_map"
        day_map.put(document_name, hour_map);
    }

    private static String index_to_hour(int index) {
        int N_SINGLE_DIGIT_NUMBERS = 10;
        if (index < N_SINGLE_DIGIT_NUMBERS) {
            return "0" + index + "-00";
        }
        return index + "-00";
    }

    private void update_hour_map(HashMap<String, Object> hour_map, int index) {
        for (OpenRange range : ranges_per_day.get(index)) {
            int size = range.getEnd_hour() - range.getStart_hour();
            for (int i = range.getStart_hour(); i < range.getEnd_hour(); ++i) {
                hour_map.put(index_to_hour(i), true);
            }
        }
    }

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