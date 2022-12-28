package com.example.yopo.data_classes;

import android.os.AsyncTask;

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
import java.util.List;
import java.util.Map;


public class GetAppointmentsListTask extends AsyncTask<Void, Void, List<HashMap<String, Object>>> {
    private static final String FUNCTION_URL = "https://us-central1-your-project-id.cloudfunctions.net/getDocumentData";

    private String collectionPath;  // Collection path
    private String username;
    private String date;
    private boolean isClient;

    public GetAppointmentsListTask(String collectionPath, String username, String date, boolean isClient) {
        this.collectionPath = collectionPath;
        this.username = username;
        this.date = date;
        this.isClient = isClient;
    }

    @Override
    protected List<HashMap<String, Object>> doInBackground(Void... voids) {
        try {
            String query = String.format("collection=%s&username=%s&date=%s&isclient=%s",
                    URLEncoder.encode(collectionPath, "UTF-8"),
                    URLEncoder.encode(username, "UTF-8"),
                    URLEncoder.encode(date, "UTF-8"),
                    isClient);
            URL url = new URL(FUNCTION_URL + "?" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                String response = readStream(connection.getInputStream());
                Gson gson = new Gson();
                Type type = new TypeToken<List<HashMap<String, Object>>>(){}.getType();
                return gson.fromJson(response, type);
            } else {
                throw new IOException("Error calling function: HTTP response code ${responseCode}");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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
