package com.example.yopo.data_classes;

import android.icu.util.LocaleData;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A Singleton class for interfacing with the database
 */
public class Database {
    private final FirebaseFirestore db;
    private static Database database = null;
    private HashMap<Integer, String> day_of_week;

    private Database() {
        db = FirebaseFirestore.getInstance();
        this.day_of_week = new HashMap<Integer, String>();
        this.day_of_week.put(1, "Monday");
        this.day_of_week.put(2, "Tuesday");
        this.day_of_week.put(3, "Wednesday");
        this.day_of_week.put(4, "Thursday");
        this.day_of_week.put(5, "Friday");
        this.day_of_week.put(6, "Saturday");
        this.day_of_week.put(7, "Sunday");
    }


    /**
     * Check if a username exists in the database
     *
     * @param username   the username to check
     * @param collection the collection in which to check for the username
     * @return True if the username is taken, else False
     */
    private boolean username_exists(String username, String collection) {
        DocumentReference userRef = db.collection("users").document(username);

        // Asynchronously retrieve the document
        Task<DocumentSnapshot> task = userRef.get();

        // wait for completion
        while (!task.isComplete()) {
        }

        // Block on the response to get the result
        DocumentSnapshot document = task.getResult();

        return document.exists();
    }

    /**
     * Get the database instance
     *
     * @return A Database object
     */
    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    /**
     * Add a new client to the database given a HashMap containing the users information,
     * where keys are attributes and values are attribute values
     *
     * @param client_data A HashMap object
     * @return True if successfully added the new user to the database, else False
     */
    public boolean add_new_client(HashMap<String, Object> client_data) {
        // check if the client exists
        if (username_exists((String) client_data.get("username"), "clients")) {
            return false;
        }

        // add new user
        DocumentReference userRef = db.collection("clients").document((String) client_data.get("username"));

        // Write the data to the document
        Task<Void> result = userRef.set(client_data);

        // wait for completion
        while (!result.isComplete()) {
        }

        return true;
    }


    /**
     * This function retrieves client info from the database using its username
     *
     * @param client_username The username of the client
     * @return A HashMap with the users data, keys are attributes and values are attribute values
     */
    public HashMap<String, Object> get_client_info(String client_username) {
        DocumentReference userRef = db.collection("clients").document(client_username);

        // Asynchronously retrieve the document
        Task<DocumentSnapshot> task = userRef.get();

        //Wait for task to finish
        while (!task.isComplete()) {
        }

        // Block on the response to get the result
        DocumentSnapshot document = task.getResult();

        // Check if the document exists
        if (document.exists()) {
            return (HashMap<String, Object>) document.getData();
            // do something with the user data
        }
        return null;
    }


    /**
     * This function added a new business user to the database given a Hashmap,
     * where the strings are the key and the objects are the values.
     */
    public boolean add_new_business(HashMap<String, Object> business_data) {
        // check if the client exists
        if (username_exists((String) business_data.get("username"), "business")) {
            return false;
        }

        // add new user
        DocumentReference userRef = db.collection("business").document((String) business_data.get("username"));

        // Write the data to the document
        Task<Void> result = userRef.set(business_data);

        // wait for completion
        while (!result.isComplete()) {
        }

        return true;
    }


    /**
     * This function allows a client to set a new appointment at a desired business
     *
     * @param appointment A HashMap that contains all the needed information about the
     *                    new appointment. such as:
     *                    client username, client name, business username, business username,
     *                    Date, Time and type of service.
     */

    public boolean add_new_appointment(HashMap<String, Object> appointment) {

        // add new user
        DocumentReference userRef = db.collection("appointments").document((String) appointment.get("appointment_id"));

        // Write the data to the document
        Task<Void> result = userRef.set(appointment);

        // wait for completion
        while (!result.isComplete()) {
        }

        return true;
    }

    /**
     * This function retrieves a list of all appointments on @Date for @username.
     *
     * @param username
     * @param Date
     * @param isClient tells whether to check on clients or business.
     * @return a list of appointments
     */
    public List<HashMap<String, Object>> get_appointment_info(String username, String Date, boolean isClient) {
        List<HashMap<String, Object>> list_of_appointments = null;
        Task<QuerySnapshot> task = null;
        if (isClient) {
            task = db.collection("appointments")
                    .whereEqualTo("client_username", username)
                    .whereEqualTo("date", Date)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("DB", document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.w("DB", "Error getting documents.", task.getException());
                            }
                        }
                    });
        } else {
            task = db.collection("appointments")
                    .whereEqualTo("business_username", username)
                    .whereEqualTo("date", Date)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("DB", document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.w("DB", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }

        while (!task.isComplete() && !task.isCanceled()) {
        }
        if (task.isComplete()) {
            if (!task.getResult().isEmpty()) {
                HashMap<String, Object> appointment_data;
                list_of_appointments = new LinkedList<>();
                for (DocumentSnapshot dic : task.getResult().getDocuments()) {
                    appointment_data = (HashMap<String, Object>) dic.getData();
                    list_of_appointments.add(appointment_data);
                    Log.d("ClientData", "" + appointment_data);
                }
            }
        }

        return list_of_appointments;
    }

    /**
     * This function retrieves client info from the database using its username
     *
     * @param business_username The username of the client
     * @return A HashMap with the users data, keys are attributes and values are attribute values
     */
    public HashMap<String, Object> get_business_info(String business_username) {
        DocumentReference userRef = db.collection("business").document(business_username);

        // Asynchronously retrieve the document
        Task<DocumentSnapshot> task = userRef.get();

        //Wait for task to finish
        while (!task.isComplete()) {
        }

        // Block on the response to get the result
        DocumentSnapshot document = task.getResult();

        // Check if the document exists
        if (document.exists()) {
            return (HashMap<String, Object>) document.getData();
        }
        return null;
    }

    public HashMap<String, Object> get_business_info_by_name(String business_name) {
        HashMap<String, Object> business_data = null;

        Task<QuerySnapshot> task = db.collection("business")
                .whereEqualTo("business_name", business_name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("DB", "Error getting documents.", task.getException());
                        }
                    }
                });

        while (!task.isComplete() && !task.isCanceled()) {
        }
        if (task.isComplete()) {
            if (!task.getResult().isEmpty()) {
                business_data = (HashMap<String, Object>) task.getResult().getDocuments().get(0).getData();
                Log.d("ClientData", "" + business_data);
            }
        }
        return business_data;
    }

    /**
     * This method adds a new service to the "Services" collection in the database.
     * {username : String, Service: String, price: String}
     *
     * @param service contains the business username and the service description
     */
    public boolean add_new_service(HashMap<String, Object> service) {

        // check if the client exists
        if (username_exists((String) service.get("service_id"), "services")) {
            return false;
        }

        // add new user
        DocumentReference userRef = db.collection("services").document((String) service.get("service_id"));

        // Write the data to the document
        Task<Void> result = userRef.set(service);

        // wait for completion
        while (!result.isComplete()) {
        }

        return true;

    }

    public HashMap<String, Object> get_service(String service_id) {
        DocumentReference userRef = db.collection("services").document(service_id);

        // Asynchronously retrieve the document
        Task<DocumentSnapshot> task = userRef.get();

        //Wait for task to finish
        while (!task.isComplete()) {
        }

        // Block on the response to get the result
        DocumentSnapshot document = task.getResult();

        // Check if the document exists
        if (document.exists()) {
            return (HashMap<String, Object>) document.getData();
            // do something with the user data
        }
        return null;

    }


    /**
     * This method retrieve the list of all business's services.
     *
     * @param username ths business username
     * @return the list of all services
     */
    public List<HashMap<String, Object>> get_services(String username) {
        List<HashMap<String, Object>> list_of_services = null;
        Task<QuerySnapshot> task = null;

        task = db.collection("services")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DB", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("DB", "Error getting documents.", task.getException());
                        }
                    }
                });

        while (!task.isComplete() && !task.isCanceled()) {
        }
        if (task.isComplete()) {
            if (!task.getResult().isEmpty()) {
                HashMap<String, Object> service_data;
                list_of_services = new LinkedList<>();
                for (DocumentSnapshot dic : task.getResult().getDocuments()) {
                    service_data = (HashMap<String, Object>) dic.getData();
                    list_of_services.add(service_data);
                    Log.d("ServiceData", "" + service_data);
                }
            }
        }

        return list_of_services;
    }

    /***
     * This function counts appointments for a certain user in a specific day
     * @param username
     * @param Date
     * @param isClient
     * @return number of appointments
     */
    public int count_appointments_on_date(String username, String Date, boolean isClient) {
        List<HashMap<String, Object>> appointments = get_appointment_info(username, Date, isClient);
        if (appointments == null)
            return 0;
        else
            return appointments.size();
    }

    /**
     * Get all the businesses in the 'businesses' collection
     *
     * @return A list of hashmaps, each hashmap represents a business
     */
    public List<HashMap<String, Object>> get_all_businesses() {
        // an array to save the businesses data in
        List<HashMap<String, Object>> business_list = null;

        // get all businesses from the database
        Task<QuerySnapshot> task = db.collection("business").get();

        // wait for task to complete
        while (!task.isComplete() && !task.isCanceled()) {
        }

        // if failed return null
        if (!task.isComplete()) {
            return null;
        }

        // get the results
        business_list = new ArrayList<>();
        List<DocumentSnapshot> doc_list = task.getResult().getDocuments();
        for (DocumentSnapshot doc : doc_list) {
            business_list.add((HashMap<String, Object>) doc.getData());
        }

        return business_list;
    }

    /**
     * Get a list of businesses if their name contains 'businessName'
     *
     * @param businessName a string of the business name
     * @return a list of businesses
     */
    public List<HashMap<String, Object>> search_business(String businessName) {
        // check that the name is not empty
        if (businessName.isEmpty()) {
            return null;
        }

        // get all businesses with a similar name
        CollectionReference usersRef = db.collection("business");

        Query query = usersRef.whereGreaterThanOrEqualTo("business_name", businessName)
                .whereLessThan("business_name", businessName + "\uf8ff");

        // Asynchronously retrieve the documents
        Task<QuerySnapshot> querySnapshot = query.get();

        // Wait for task to complete
        while (!querySnapshot.isComplete()) {
        }

        // Block on the response to get the result
        List<DocumentSnapshot> documents = querySnapshot.getResult().getDocuments();
        List<HashMap<String, Object>> businesses = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            businesses.add((HashMap<String, Object>) document.getData());
            Log.d("BusinessName", document.getId());
        }

        return businesses;
    }

    public boolean validate_email(String email_address) {
        return Patterns.EMAIL_ADDRESS.matcher(email_address).matches();
    }

    public boolean add_to_collection(HashMap<String, Object> data, String document_name, String collection) {
        // add new data
        DocumentReference userRef = db.collection(collection).document(document_name);

        // Write the data to the document
        Task<Void> result = userRef.set(data);

        // wait for completion
        while (!result.isComplete()) {
        }

        return true;
    }

    public HashMap<String, Object> get_open_range_by_day(String username, String day) {
        DocumentReference userRef = db.collection("business_open_times").document(username + "-" + day);

        // Asynchronously retrieve the document
        Task<DocumentSnapshot> task = userRef.get();

        //Wait for task to finish
        while (!task.isComplete()) {
        }

        // get the result
        return (HashMap<String, Object>) task.getResult().getData();
    }


    public HashMap<String, Object> get_open_range_by_full_date(String username, String date){
        String[] date_parts = date.split("/");
        LocalDate date_obj = LocalDate.of(Integer.parseInt(date_parts[2]), Integer.parseInt(date_parts[1]), Integer.parseInt(date_parts[0]));
        int day_int = getDayNumberNew(date_obj);
        String day_str = this.day_of_week.get(day_int);
        return this.get_open_range_by_day(username, day_str);
    }

    public static int getDayNumberNew(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }


}
