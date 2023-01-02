package com.example.yopo.layout_classes;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yopo.R;
import com.example.yopo.data_classes.Database;
import com.example.yopo.data_classes.Server;
import com.example.yopo.data_classes.Session;
import com.example.yopo.util_classes.RecyclerTouchListener;
import com.example.yopo.util_classes.Service;
import com.example.yopo.util_classes.ServiceAdapter;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BusinessEditServicesActivity extends AppCompatActivity {
    // get variables
    private RecyclerView service_list;
    private Button add_services_button;

    // database and session variables
    private Session session;
    private Database database;
    private Server server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_edit_services_layout);

        // get database and session instances
        database = Database.getInstance();
        session = Session.getInstance();
        server = Server.getInstance();

        // get recycler view
        service_list = findViewById(R.id.service_list);
        add_services_button = findViewById(R.id.add_services_button);

        // create a random array for testing
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
                // code taken from: https://stackoverflow.com/questions/5944987/how-to-create-a-popup-window-popupwindow-in-android

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window_layout, null);
                TextView delete = popupView.findViewById(R.id.delete);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup to dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

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
                        Log.i("DELETE", "DELETE");
                        popupWindow.dismiss();
                    }
                });
            }
        }));

        add_services_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BusinessEditServicesActivity.this, BusinessEditServicesAddActivity.class);
                startActivity(i);
            }
        });
    }
}
