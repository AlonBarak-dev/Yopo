package com.example.yopo.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import com.google.gson.Gson;

public class RemoveFromFirestoreTask extends AsyncTask<Void, Void, Void>{
    private static final String FUNCTION_URL = "https://us-central1-yopo-6aaec.cloudfunctions.net/removeFromFirestore";

    private String data;  // document key to be removed to Firestore
    private String collectionPath;
    private String username;

    public RemoveFromFirestoreTask(String data) {
        this.data = data;
    }

    public RemoveFromFirestoreTask(String data, String collectionPath, String username) {
        this.data = data;
        this.collectionPath = collectionPath;
        this.username = username;

    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            String query = String.format("collection=%s&username=%s",
                    URLEncoder.encode(collectionPath, "UTF-8"),
                    URLEncoder.encode(username, "UTF-8"));
            URL url = new URL(FUNCTION_URL + "?" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data.getBytes("UTF-8"));
            Log.d("Firestore", "Sent message to the Server");
            outputStream.close();

            connection.getResponseCode();  // Check for errors
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
