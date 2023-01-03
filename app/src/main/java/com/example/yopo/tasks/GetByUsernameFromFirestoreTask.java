package com.example.yopo.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class GetByUsernameFromFirestoreTask extends AsyncTask<Void, Void, HashMap<String, HashMap<String, Object>>>{
    private static final String FUNCTION_URL = "https://us-central1-yopo-6aaec.cloudfunctions.net/getFromFirestore";

    private String username;  // document key to be removed to Firestore
    private String collectionPath;

    public GetByUsernameFromFirestoreTask(String data) {
        this.username = data;
    }


    // For a given username
    public GetByUsernameFromFirestoreTask(String data, String collectionPath) {
        this.username = data;
        this.collectionPath = collectionPath;
    }

    @Override
    protected HashMap<String, HashMap<String, Object>> doInBackground(Void... voids) {
        try {
            // define the query
            String query = String.format("collection=%s&document=%s",
                    URLEncoder.encode(collectionPath, "UTF-8"),
                    URLEncoder.encode(username, "UTF-8"));

            URL url = new URL(FUNCTION_URL + "?" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // read the response from the server and convert it to string
                String response = readStream(connection.getInputStream());
//                Log.d("TEST", response);
                Gson gson = new Gson();
                Type type = new TypeToken<HashMap<String, HashMap<String, Object>>>(){}.getType();
                // return the HashMap that the server returned
                return gson.fromJson(response, type);
            } else {
                throw new IOException("Error calling function: HTTP response code " + responseCode);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return null;
    }

    // This method receive an InputStream and output the String from it
    private String readStream(InputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}
