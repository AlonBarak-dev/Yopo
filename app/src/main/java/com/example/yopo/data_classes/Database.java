package com.example.yopo.data_classes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

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
     * TODO make it so that if i get an error from this function, return false instead of true
     *
     * @param client_data A HashMap object
     * @return True if successfully added the new user to the database, else False
     */
    public boolean add_new_client(HashMap<String, Object> client_data) {
        // Add a new document with a generated ID
        db.collection("clients")
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
            client_data = (HashMap<String, Object>) task.getResult().getDocuments().get(0).getData();
            Log.d("ClientData", "" + client_data);
        }
        return client_data;
    }


}
