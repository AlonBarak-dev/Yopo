package com.example.yopo.data_classes;

import java.util.HashMap;

public class Session {
    private final HashMap<String, Object> session_data;
    private static Session session = null;

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }

        return session;
    }

    private Session() {
        session_data = new HashMap<>();
    }

    public void add_session_attribute(String key, Object value) {
        session_data.put(key, value);
    }

    public Object get_session_attribute(String key) {
        return this.session_data.get(key);
    }
}
