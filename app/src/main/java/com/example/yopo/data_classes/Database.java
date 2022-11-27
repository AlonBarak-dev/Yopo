package com.example.yopo.data_classes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import io.grpc.util.TransmitStatusRuntimeExceptionInterceptor;

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
}
