package com.example.yopo.layout_classes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yopo.R;
import com.example.yopo.data_classes.ServerFactory;
import com.example.yopo.data_classes.Session;
import com.example.yopo.interfaces.Server;
import com.example.yopo.util_classes.BusinessesAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;

public class ClientSearchActivity extends AppCompatActivity {
    private TextInputLayout search_bar;
    private ImageButton search_button;
    private RecyclerView search_view;

    private Server server;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_search_layout);

        initializeServerAndSession();
        initializeFields();
        initializeButtonOnClickEvent();
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
        search_button = findViewById(R.id.search_button);
        search_bar = findViewById(R.id.search_bar);
        search_view = findViewById(R.id.search_view);

    }

    private void initializeButtonOnClickEvent() {
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ClientSearchActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
                List<HashMap<String, Object>> businesses = getListOfBusinesses();

                session.add_session_attribute("list_of_businesses", businesses);

                if (businesses != null && !businesses.isEmpty()) {
                    loadBusinessesToRecycler(businesses);
                } else {
                    Toast.makeText(ClientSearchActivity.this, "Business Not Found!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private List<HashMap<String, Object>> getListOfBusinesses() {
        String search_query_name = search_bar.getEditText().getText().toString();
        List<HashMap<String, Object>> businesses = server.search_business(search_query_name);

        return businesses;
    }

    private void loadBusinessesToRecycler(List<HashMap<String, Object>> businesses) {
        // create adapter
        BusinessesAdapter adapter = new BusinessesAdapter(ClientSearchActivity.this, businesses);

        // create layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ClientSearchActivity.this, LinearLayoutManager.VERTICAL, false);

        // set layout manager and adapter
        search_view.setLayoutManager(linearLayoutManager);
        search_view.setAdapter(adapter);
    }
}