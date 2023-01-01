package com.example.yopo.layout_classes;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Server;
import com.example.yopo.data_classes.Session;
import com.example.yopo.util_classes.BusinessesAdapter;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;

public class ClientSearchActivity extends AppCompatActivity {
    private TextInputLayout search_bar;
    private ImageButton search_button;
    private RecyclerView search_view;

    private Database database;
    private Server server;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_search_layout);

        // get database
        database = Database.getInstance();

        server = Server.getInstance();

        // get session
        session = Session.getInstance();

        // get the field variables
        search_button = findViewById(R.id.search_button);
        search_bar = findViewById(R.id.search_bar);
        search_view = findViewById(R.id.search_view);

        // set event to button click
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ClientSearchActivity.this, "Searching...", Toast.LENGTH_SHORT).show();

                // get business by the given name
                String search_query_name = search_bar.getEditText().getText().toString();

                // get list of businesses
                List<HashMap<String, Object>> businesses = server.search_business(search_query_name);

                // save list of businesses
                session.add_session_attribute("list_of_businesses", businesses);

                // validate
                if (businesses != null) {
                    // create adapter
                    BusinessesAdapter adapter = new BusinessesAdapter(ClientSearchActivity.this, businesses);

                    // create layout manager
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ClientSearchActivity.this, LinearLayoutManager.VERTICAL, false);

                    // set layout manager and adapter
                    search_view.setLayoutManager(linearLayoutManager);
                    search_view.setAdapter(adapter);

                } else {
                    Toast.makeText(ClientSearchActivity.this, "Business Not Found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}