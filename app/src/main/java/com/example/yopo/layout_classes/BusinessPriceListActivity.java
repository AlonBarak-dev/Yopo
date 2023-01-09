package com.example.yopo.layout_classes;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yopo.R;
import com.example.yopo.data_classes.ServerFactory;
import com.example.yopo.data_classes.Session;
import com.example.yopo.interfaces.Server;
import com.example.yopo.util_classes.Service;
import com.example.yopo.util_classes.ServiceAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusinessPriceListActivity extends AppCompatActivity {
    // get variables
    private RecyclerView price_list;

    // database and session variables
    private Session session;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_price_list_layout);

        initializeServerAndSession();
        initializeFields();
        initializeRecycler();
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
        price_list = findViewById(R.id.price_list);
    }

    private List<Service> createListOfServices() {
        List<HashMap<String, Object>> list_of_services = server.get_services((String) session.get_session_attribute("business_username"));
        List<Service> services = new ArrayList<>();
        for (HashMap<String, Object> map : list_of_services) {
            try {
                Service service = new Service((String) map.get("service"), Float.parseFloat((String) map.get("price")), (String) map.get("service_id"));
                services.add(service);
            } catch (Exception ignored) {

            }
        }

        return services;
    }

    private void initializeRecycler() {
        List<Service> services = createListOfServices();
        ServiceAdapter adapter = new ServiceAdapter(this, services);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        price_list.setLayoutManager(linearLayoutManager);
        price_list.setAdapter(adapter);
    }
}