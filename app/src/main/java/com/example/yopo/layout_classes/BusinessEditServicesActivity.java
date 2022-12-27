package com.example.yopo.layout_classes;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Session;
import com.example.yopo.util_classes.RecyclerTouchListener;
import com.example.yopo.util_classes.Service;
import com.example.yopo.util_classes.ServiceAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusinessEditServicesActivity extends AppCompatActivity {
    // get variables
    private RecyclerView service_list;

    // database and session variables
    private Session session;
    private Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_edit_services_layout);

        // get database and session instances
        database = Database.getInstance();
        session = Session.getInstance();

        // get recycler view
        service_list = findViewById(R.id.service_list);

        // create a random array for testing
        List<HashMap<String, Object>> list_of_services = database.get_services((String) session.get_session_attribute("username"));
        List<Service> services = new ArrayList<>();

        if (session.get_session_attribute("username") == null) {
            Log.e("BusinessEditServices", "Session: username is null!");
        }
        else {
            Log.v("BusinessEditServices", session.get_session_attribute("username").toString());
        }

        if (list_of_services == null) {
            Log.e("BusinessEditServices", "list_of_services is null!");
        }
        else {
            Log.v("BusinessEditServices", "list of services size: "+list_of_services.size());
        }

        for (HashMap<String, Object> map : list_of_services) {
            try {
                Service service = new Service((String) map.get("service"), Float.parseFloat((String) map.get("price")), (String) map.get("service_id"));
                services.add(service);
            } catch (Exception ignored) {

            }
        }

        // create adapter
        ServiceAdapter adapter = new ServiceAdapter(this, services);

        // create layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // set layout manager and adapter
        service_list.setLayoutManager(linearLayoutManager);
        service_list.setAdapter(adapter);

        // service list item click listener
        service_list.addOnItemTouchListener(new RecyclerTouchListener(service_list.getContext(), service_list, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));




    }



}
