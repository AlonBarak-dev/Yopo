package com.example.yopo.data_classes;

import android.util.Log;

import com.example.yopo.interfaces.IServer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.net.*;
import java.io.*;

public class Server implements IServer {

    private static Server server = null;
    private static Socket socket = null;
    private PrintWriter out;
    private BufferedReader in;


    private Server(){
        try {
            this.socket = new Socket("127.0.0.1", 2000);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }
        catch (Exception e){
            Log.d("Client", "Cant connect Server");
        }
    }

    public static Server getInstance(){
        if (server == null){
            server = new Server();
        }
        return server;
    }



    @Override
    public boolean username_exists(String username, String collection) {
        return false;
    }

    @Override
    public boolean add_new_client(HashMap<String, Object> client_data) {
        return false;
    }

    @Override
    public HashMap<String, Object> get_client_info(String client_username) {
        return null;
    }

    @Override
    public boolean add_new_business(HashMap<String, Object> business_data) {
        return false;
    }

    @Override
    public boolean add_new_appointment(HashMap<String, Object> appointment) {
        return false;
    }

    @Override
    public List<HashMap<String, Object>> get_appointment_info(String username, String Date, boolean isClient) {
        return null;
    }

    @Override
    public HashMap<String, Object> get_business_info(String business_username) {
        return null;
    }

    @Override
    public HashMap<String, Object> get_business_info_by_name(String business_name) {
        return null;
    }

    @Override
    public boolean add_new_service(HashMap<String, Object> service) {
        return false;
    }

    @Override
    public HashMap<String, Object> get_service(String service_id) {
        return null;
    }

    @Override
    public List<HashMap<String, Object>> get_services(String username) {
        return null;
    }

    @Override
    public int count_appointments_on_date(String username, String Date, boolean isClient) {
        return 0;
    }

    @Override
    public List<HashMap<String, Object>> get_all_businesses() {
        return null;
    }

    @Override
    public List<HashMap<String, Object>> search_business(String businessName) {
        return null;
    }

    @Override
    public boolean validate_email(String email_address) {
        return false;
    }

    @Override
    public boolean add_to_collection(HashMap<String, Object> data, String document_name, String collection) {
        return false;
    }

    @Override
    public HashMap<String, Object> get_open_range_by_day(String username, String day) {
        return null;
    }

    @Override
    public HashMap<String, Object> get_open_range_by_full_date(String username, String date) {
        return null;
    }

    @Override
    public int getDayNumberNew(LocalDate date) {
        return 0;
    }
}
