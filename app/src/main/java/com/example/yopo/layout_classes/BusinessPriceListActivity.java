package com.example.yopo.layout_classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Session;
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
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_price_list_layout);

        // get database and session instances
        database = Database.getInstance();
        session = Session.getInstance();

        // get recycler view
        price_list = findViewById(R.id.price_list);

        // create a random array for testing
        List<HashMap<String, Object>> list_of_services = database.get_services((String) session.get_session_attribute("business_username"));
        List<Service> services = new ArrayList<>();
        for (HashMap<String, Object> map : list_of_services) {
            try {
                Service service = new Service((String) map.get("service"), Float.parseFloat((String) map.get("price")));
                services.add(service);
            } catch (Exception ignored) {

            }
        }

        // create adapter
        ServiceAdapter adapter = new ServiceAdapter(this, services);

        // create layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // set layout manager and adapter
        price_list.setLayoutManager(linearLayoutManager);
        price_list.setAdapter(adapter);

    }
}