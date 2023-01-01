package com.example.yopo.data_classes;

import android.os.AsyncTask;
import android.util.Patterns;

import com.example.yopo.interfaces.IServer;
import com.example.yopo.tasks.TaskFactory;
import com.example.yopo.tasks.TaskType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/*
This class is an implementation of the IServer interface which is responsible for the
communication with the Firebase Server.
This class is a Singleton class.
//TODO implement the entire class
 */
public class Server implements IServer {


    private static Server server = null;
    private TaskFactory factory;
    private HashMap<Integer, String> day_of_week;


    private Server() {
        factory = new TaskFactory();
        this.day_of_week = new HashMap<Integer, String>();
        this.day_of_week.put(1, "Monday");
        this.day_of_week.put(2, "Tuesday");
        this.day_of_week.put(3, "Wednesday");
        this.day_of_week.put(4, "Thursday");
        this.day_of_week.put(5, "Friday");
        this.day_of_week.put(6, "Saturday");
        this.day_of_week.put(7, "Sunday");
    }

    public static Server getInstance() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }


    @Override
    public boolean username_exists(String username, String collection) {
        if (collection.equals("clients"))
            return get_client_info(username) != null;
        else
            return get_business_info(username) != null;
    }

    @Override
    public boolean add_new_client(HashMap<String, Object> client_data) {
        return add_to_collection(client_data, (String) client_data.get("username"), "clients");
    }

    @Override
    public HashMap<String, Object> get_client_info(String client_username) {
        try {
            AsyncTask<Void, Void, HashMap<String, HashMap<String, Object>>> task = factory.get_task(TaskType.GET, "clients", client_username, null);
            HashMap<String, HashMap<String, Object>> result = task.execute().get();
            return result.get("data");
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean add_new_business(HashMap<String, Object> business_data) {
        return add_to_collection(business_data, (String) business_data.get("username"), "business");
    }

    @Override
    public boolean add_new_appointment(HashMap<String, Object> appointment) {
        return add_to_collection(appointment, (String) appointment.get("appointment_id"), "appointments");
    }

    @Override
    public boolean add_new_service(HashMap<String, Object> service) {
        return add_to_collection(service, (String) service.get("service_id"), "services");
    }

    @Override
    public List<HashMap<String, Object>> get_appointment_info(String username, String Date, boolean isClient) {
        try {
            HashMap<String, Object> info = new HashMap<String, Object>();
            info.put("date", Date);
            info.put("is_client", isClient);
            AsyncTask<Void, Void, List<HashMap<String, HashMap<String, Object>>>> task = factory.get_task(TaskType.GET_MANY, "appointments", username, info);
            List<HashMap<String, HashMap<String, Object>>> result = task.execute().get();
            List<HashMap<String, Object>> ex_result = extractHashMap(result);
            return ex_result;

        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public HashMap<String, Object> get_business_info(String business_username) {
        try {
            AsyncTask<Void, Void, HashMap<String, HashMap<String, Object>>> task = factory.get_task(TaskType.GET, "business", business_username, null);
            HashMap<String, HashMap<String, Object>> result = task.execute().get();
            return result.get("data");
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public HashMap<String, Object> get_business_info_by_name(String business_name) {
        try {
            AsyncTask<Void, Void, HashMap<String, HashMap<String, Object>>> task = factory.get_task(TaskType.GET_NAME, "business", business_name, null);
            HashMap<String, HashMap<String, Object>> result = task.execute().get();
            return result.get("data");
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public HashMap<String, Object> get_service(String service_id) {
        try {
            AsyncTask<Void, Void, HashMap<String, HashMap<String, Object>>> task = factory.get_task(TaskType.GET, "services", service_id, null);
            HashMap<String, HashMap<String, Object>> result = task.execute().get();
            return result.get("data");
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public List<HashMap<String, Object>> get_services(String username) {
        try {
            AsyncTask<Void, Void, List<HashMap<String, HashMap<String, Object>>>> task = factory.get_task(TaskType.GET_MANY, "services", username, null);
            List<HashMap<String, HashMap<String, Object>>> result = task.execute().get();
            List<HashMap<String, Object>> ex_result = extractHashMap(result);
            return ex_result;
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public int count_appointments_on_date(String username, String Date, boolean isClient) {
        try {
            HashMap<String, Object> info = new HashMap<String, Object>();
            info.put("date", Date);
            info.put("is_client", isClient);
            AsyncTask<Void, Void, List<HashMap<String, HashMap<String, Object>>>> task = factory.get_task(TaskType.GET_MANY, "appointments", username, info);
            List<HashMap<String, HashMap<String, Object>>> result = task.execute().get();
            List<HashMap<String, Object>> ex_result = extractHashMap(result);
            return ex_result.size();

        }
        catch (Exception e){
            return 0;
        }
    }

    @Override
    public List<HashMap<String, Object>> get_all_businesses() {
        try{
            AsyncTask<Void, Void, List<HashMap<String, HashMap<String, Object>>>> task = factory.get_task(TaskType.GET_ALL, "business",null, null);
            List<HashMap<String, HashMap<String, Object>>> result = task.execute().get();
            List<HashMap<String, Object>> ex_result = extractHashMap(result);
            return ex_result;
        }
        catch (Exception e){
            return null;
        }

    }

    @Override
    public List<HashMap<String, Object>> search_business(String businessName) {
        return null;
    }

    @Override
    public boolean validate_email(String email_address) {
        return Patterns.EMAIL_ADDRESS.matcher(email_address).matches();
    }

    @Override
    public boolean add_to_collection(HashMap<String, Object> data, String document_name, String collection) {
        AsyncTask<Void, Void, Void> task = factory.get_task(TaskType.ADD, collection, document_name, data);
        task.execute();
        return true;
    }

    @Override
    public HashMap<String, Object> get_open_range_by_day(String username, String day) {
        try {
            AsyncTask<Void, Void, HashMap<String, HashMap<String, Object>>> task = factory.get_task(TaskType.GET, "business_open_times", username + "-" + day, null);
            HashMap<String, HashMap<String, Object>> result = task.execute().get();
            return result.get("data");
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public HashMap<String, Object> get_open_range_by_full_date(String username, String date) {
        String[] date_parts = date.split("/");
        LocalDate date_obj = LocalDate.of(Integer.parseInt(date_parts[2]), Integer.parseInt(date_parts[1]), Integer.parseInt(date_parts[0]));
        int day_int = getDayNumberNew(date_obj);
        String day_str = this.day_of_week.get(day_int);
        return this.get_open_range_by_day(username, day_str);
    }

    @Override
    public int getDayNumberNew(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }


    private List<HashMap<String, Object>> extractHashMap(List<HashMap<String, HashMap<String, Object>>> appointments){
        List<HashMap<String, Object>> result = new LinkedList<HashMap<String, Object>>();
        for (HashMap<String, HashMap<String, Object>> item : appointments){
            HashMap<String, Object> ex_item = item.get("doc");
            result.add(ex_item);
        }
        return result;
    }



}
