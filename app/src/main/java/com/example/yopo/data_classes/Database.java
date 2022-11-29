package com.example.yopo.data_classes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A Singleton class for interfacing with the database
 */
public class Database {
    private final FirebaseFirestore db;
    private static Database database = null;

    private Database() {
        db = FirebaseFirestore.getInstance();
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
     * <p>
     *
     * @param client_data A HashMap object
     * @return True if successfully added the new user to the database, else False
     */
    public boolean add_new_client(HashMap<String, Object> client_data) {
        // Add a new document with a generated ID
        Task<DocumentReference> task = db.collection("clients")
                .add(client_data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DB", "Client document added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DB", "Error adding document", e);
                    }
                });


        while (!task.isComplete() && !task.isCanceled()) {
        }

        return !task.isCanceled();
    }

    /**
     * This function retrieves client info from the database using its username
     *
     * @param client_username The username of the client
     * @return A HashMap with the users data, keys are attributes and values are attribute values
     */
    public HashMap<String, Object> get_client_info(String client_username) {
        HashMap<String, Object> client_data = null;

        Task<QuerySnapshot> task = db.collection("clients")
                .whereEqualTo("username", client_username)
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
                client_data = (HashMap<String, Object>) task.getResult().getDocuments().get(0).getData();
                Log.d("ClientData", "" + client_data);
            }
        }
        return client_data;
    }


    /**
     * This function added a new business user to the database given a Hashmap,
     * where the strings are the key and the objects are the values.
     */
    public boolean add_new_business(HashMap<String, Object> client_data) {
        // Add a new document with a generated ID
        db.collection("business")
                .add(client_data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DB", "Client document added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DB", "Error adding document", e);
                    }
                });

        return true;
    }


    /**
     * This function retrieves client info from the database using its username
     *
     * @param business_username The username of the client
     * @return A HashMap with the users data, keys are attributes and values are attribute values
     */
    public HashMap<String, Object> get_business_info(String business_username) {
        HashMap<String, Object> business_data = null;

        Task<QuerySnapshot> task = db.collection("business")
                .whereEqualTo("username", business_username)
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
     * This function allows a client to set a new appointment at a desired business
     *
     * @param appointment := A HashMap that contains all the needed information about the
     *                    new appointment. such as:
     *                    client username, client name, business username, business username,
     *                    Date, Time and type of treatment.
     */

    public boolean add_new_appointment(HashMap<String, Object> appointment){

        db.collection("appointments")
                .add(appointment)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("DB", "Client document added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DB", "Error adding document", e);
                    }
                });

        return true;
    }

    /**
     * This function retrieves a list of all appointments on @Date for @username.
     * @param username
     * @param Date
     * @param isClient := tells whether to check on clients or business.
     * @return a list of appointments
     */
    public List<HashMap<String, Object>> get_appointment_info(String username, String Date, boolean isClient){

        List<HashMap<String, Object>> list_of_appointments = new LinkedList<>();
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
        }
        else{
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
                for (DocumentSnapshot dic : task.getResult().getDocuments()) {
                    appointment_data = (HashMap<String, Object>) dic.getData();
                    list_of_appointments.add(appointment_data);
                    Log.d("ClientData", "" + appointment_data);
                }
            }
        }

        return list_of_appointments;
    }








}
