package com.example.yopo.data_classes;

import com.example.yopo.interfaces.Server;

public class ServerFactory {
    public static Server getServer(String server_type) throws ClassNotFoundException {
        if (server_type.equals("firestore")) {
            return FirebaseServer.getInstance();
        }
        throw new ClassNotFoundException("No such server type: " + server_type);
    }
}
