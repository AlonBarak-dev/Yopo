package com.example.yopo.interfaces;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface IServer {

    public boolean username_exists(String username, String collection);

    public boolean add_new_client(HashMap<String, Object> client_data);

    public HashMap<String, Object> get_client_info(String client_username);

    public boolean add_new_business(HashMap<String, Object> business_data);

    public boolean add_new_appointment(HashMap<String, Object> appointment);

    public List<HashMap<String, Object>> get_appointment_info(String username, String Date, boolean isClient);

    public HashMap<String, Object> get_business_info(String business_username);

    public HashMap<String, Object> get_business_info_by_name(String business_name);

    public boolean add_new_service(HashMap<String, Object> service);

    public HashMap<String, Object> get_service(String service_id);

    public List<HashMap<String, Object>> get_services(String username);

    public int count_appointments_on_date(String username, String Date, boolean isClient);

    public List<HashMap<String, Object>> get_all_businesses();

    public List<HashMap<String, Object>> search_business(String businessName);

    public boolean validate_email(String email_address);

    public boolean add_to_collection(HashMap<String, Object> data, String document_name, String collection);

    public HashMap<String, Object> get_open_range_by_day(String username, String day);

    public HashMap<String, Object> get_open_range_by_full_date(String username, String date);

    public int getDayNumberNew(LocalDate date);

    public boolean remove_service(String collection, String service_id);

}
