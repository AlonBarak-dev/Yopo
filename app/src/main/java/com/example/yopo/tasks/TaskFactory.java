package com.example.yopo.tasks;

import android.os.AsyncTask;

import java.util.HashMap;

public class TaskFactory {
    public AsyncTask get_task(TaskType type, String collection, String document, HashMap<String, Object> data) {
        if (type == TaskType.ADD) {
            return new AddToFirestoreTask(data, collection, document);
        } else if (type == TaskType.GET) {
            return new GetByUsernameFromFirestoreTask(document, collection);
        } else if (type == TaskType.GET_MANY) {
            if (collection.equals("appointments"))
                // quick explanation for my idea of how i try doing this appointments,
                // add the fields of 'is_client' and 'date' to the data hashmap as "is_client" and "date"
                // when calling this task and it will call the proper task
                return new GetAppointmentsListTask(collection, document, (String) data.get("date"), (Boolean) data.get("is_client"));
            else if (collection.equals("services"))
                return new GetServicesListTask(collection, document);
        }

        return null;
    }
}
