package com.example.yopo.data_classes;

import com.example.yopo.interfaces.IServer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import android.os.AsyncTask;

/*
This class is an implementation of the IServer interface which is responsible for the
communication with the Firebase Server.
This class is a Singleton class.
//TODO add factory that will handle the Task creations
//TODO implement the entire class
 */
public class Server implements IServer {


    private static Server server = null;


    private Server(){

    }


    @Override
    public boolean username_exists(String username, String collection) {
        return false;
    }

    @Override
    public boolean add_new_client(HashMap<String, Object> client_data) {
        AddToFirestoreTask task = new AddToFirestoreTask(client_data, "clients", (String) client_data.get("username"));
        task.execute();
        return true;
    }

    @Override
    public HashMap<String, Object> get_client_info(String client_username) {
        return null;
    }

    @Override
    public boolean add_new_business(HashMap<String, Object> business_data) {
        AddToFirestoreTask task = new AddToFirestoreTask(business_data, "business", (String) business_data.get("username"));
        task.execute();
        return true;
    }

    @Override
    public boolean add_new_appointment(HashMap<String, Object> appointment) {
        AddToFirestoreTask task = new AddToFirestoreTask(appointment, "appointments", (String) appointment.get("appointment_id"));
        task.execute();
        return true;
    }

    @Override
    public boolean add_new_service(HashMap<String, Object> service) {
        AddToFirestoreTask task = new AddToFirestoreTask(service, "services", (String) service.get("service_id"));
        task.execute();
        return true;    }

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
        AddToFirestoreTask task = new AddToFirestoreTask(data, collection, document_name);
        task.execute();
        return true;
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
