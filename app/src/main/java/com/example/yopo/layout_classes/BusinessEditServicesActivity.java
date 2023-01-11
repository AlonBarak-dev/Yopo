package com.example.yopo.layout_classes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yopo.R;
import com.example.yopo.data_classes.FirebaseServer;
import com.example.yopo.data_classes.ServerFactory;
import com.example.yopo.data_classes.Session;
import com.example.yopo.interfaces.Server;
import com.example.yopo.util_classes.RecyclerTouchListener;
import com.example.yopo.util_classes.Service;
import com.example.yopo.util_classes.ServiceAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusinessEditServicesActivity extends AppCompatActivity {
    // get variables
    private RecyclerView service_list;
    private Button add_services_button;

    // database and session variables
    private Session session;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_edit_services_layout);

        initializeServerAnsSession();
        initializeFields();
        initializeRecycler();
        initializeButtonsOnClickEvent();
        initializeRecyclerOnClickEvents();
    }

    private void initializeServerAnsSession() {
        try {
            server = ServerFactory.getServer("firestore");
        } catch (ClassNotFoundException e){
            Log.e("ServerFactory", e.toString());
        }
        session = Session.getInstance();
    }

    private void initializeFields() {
        service_list = findViewById(R.id.service_list);
        add_services_button = findViewById(R.id.add_services_button);
    }

    private void initializeButtonsOnClickEvent() {
        add_services_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessEditServicesActivity.this, BusinessEditServicesAddActivity.class);
                startActivity(i);
            }
        });
    }

    private List<Service> createListOfServices() {
        List<HashMap<String, Object>> list_of_services = server.get_services((String) session.get_session_attribute("username"));
        List<Service> services = new ArrayList<>();

        if (session.get_session_attribute("username") == null) {
            Log.e("BusinessEditServices", "Session: username is null!");
        } else {
            Log.v("BusinessEditServices", session.get_session_attribute("username").toString());
        }

        if (list_of_services == null) {
            Log.e("BusinessEditServices", "list_of_services is null!");
        } else {
            Log.v("BusinessEditServices", "list of services size: " + list_of_services.size());
        }

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

        service_list.setLayoutManager(linearLayoutManager);
        service_list.setAdapter(adapter);
    }

    private void initializeRecyclerOnClickEvents() {
        List<HashMap<String, Object>> list_of_services = server.get_services((String) session.get_session_attribute("username"));

        // service list item click listener
        service_list.addOnItemTouchListener(new RecyclerTouchListener(service_list.getContext(), service_list, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                // code taken from: https://stackoverflow.com/questions/5944987/how-to-create-a-popup-window-popupwindow-in-android

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window_layout, null);
                TextView delete = popupView.findViewById(R.id.delete);

                // create the popup window
                final PopupWindow popupWindow = createPopupWindow(popupView);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window token
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    popupWindow.setElevation(30);
                }
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // delete service and dismiss the popup window when 'DELETE' is clicked
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        server.remove_service("services", list_of_services.get(position).get("service_id").toString());
                        Log.i("DELETE", "DELETE");
                        popupWindow.dismiss();
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                });
            }
        }));
    }

    private PopupWindow createPopupWindow(View popupView) {
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup to dismiss it

        return new PopupWindow(popupView, width, height, focusable);
    }
}
