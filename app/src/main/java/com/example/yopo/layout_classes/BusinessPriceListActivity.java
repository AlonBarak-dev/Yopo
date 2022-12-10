package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.yopo.R;
import com.example.yopo.util_classes.Service;
import com.example.yopo.util_classes.ServiceAdapter;

import java.util.ArrayList;

public class BusinessPriceListActivity extends AppCompatActivity {
    // get variables
    private RecyclerView price_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_price_list_layout);

        // get recycler view
        price_list = findViewById(R.id.price_list);

        // create a random array for testing
        ArrayList<Service> services = new ArrayList<>();
        services.add(new Service("service 1",  10.2f));
        services.add(new Service("service 2", 10.2f));
        services.add(new Service("service 3", 10.2f));
        services.add(new Service("service 4", 10.2f));
        services.add(new Service("service 5", 10.2f));
        services.add(new Service("service 6", 10.2f));
        services.add(new Service("service 7", 10.2f));
        services.add(new Service("service 8", 10.2f));
        services.add(new Service("service 9", 10.2f));
        services.add(new Service("service 10", 10.2f));

        // create adapter
        ServiceAdapter adapter = new ServiceAdapter(this, services);

        // create layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // set layout manager and adapter
        price_list.setLayoutManager(linearLayoutManager);
        price_list.setAdapter(adapter);

    }
}