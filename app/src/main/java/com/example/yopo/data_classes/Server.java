package com.example.yopo.data_classes;

import android.util.Log;

import com.example.yopo.interfaces.IServer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.net.*;
import java.io.*;
import com.example.yopo.util_classes.Message;

public class Server implements IServer {

    private static Server server = null;
    private static Socket socket = null;
    private PrintWriter out;
    private BufferedReader in;


    private Server(){
        try {
            this.socket = new Socket("127.0.0.1", 2000);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }
        catch (Exception e){
            Log.d("Client", "Cant connect Server");
        }
    }

    public static Server getInstance(){
        if (server == null){
            server = new Server();
        }
        return server;
    }


    @Override
    public boolean username_exists(String username, String collection) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("collection", collection);
        Message msg = new Message("Get Username", args);
        HashMap<String, Object> result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (HashMap<String, Object>) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result == null;

    }

    @Override
    public boolean add_new_client(HashMap<String, Object> client_data) {
        Message msg = new Message("Add Client", client_data);
        Boolean result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (Boolean) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HashMap<String, Object> get_client_info(String client_username) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("username", client_username);
        Message msg = new Message("Get Client", args);
        HashMap<String, Object> result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (HashMap<String, Object>) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean add_new_business(HashMap<String, Object> business_data) {
        Message msg = new Message("Add Business", business_data);
        Boolean result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (Boolean) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result == null;
    }

    @Override
    public boolean add_new_appointment(HashMap<String, Object> appointment) {
        Message msg = new Message("Add Appointment", appointment);
        Boolean result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (Boolean) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result == null;

    }

    @Override
    public List<HashMap<String, Object>> get_appointment_info(String username, String Date, boolean isClient) {
        return null;
    }

    @Override
    public HashMap<String, Object> get_business_info(String business_username) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("username", business_username);
        Message msg = new Message("Get Business by username", args);
        HashMap<String, Object> result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (HashMap<String, Object>) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HashMap<String, Object> get_business_info_by_name(String business_name) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("name", business_name);
        Message msg = new Message("Get Business by name", args);
        HashMap<String, Object> result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (HashMap<String, Object>) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public boolean add_new_service(HashMap<String, Object> service) {
        Message msg = new Message("Add Service", service);
        Boolean result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (Boolean) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result == null;
    }

    @Override
    public HashMap<String, Object> get_service(String service_id) {
        return null;
    }

    @Override
    public List<HashMap<String, Object>> get_services(String username) {
        return null;
    }

    @Override
    public int count_appointments_on_date(String username, String Date, boolean isClient) {
        return 0;
    }

    @Override
    public List<HashMap<String, Object>> get_all_businesses() {
        return null;
    }

    @Override
    public List<HashMap<String, Object>> search_business(String businessName) {
        return null;
    }

    @Override
    public boolean validate_email(String email_address) {
        return false;
    }

    @Override
    public boolean add_to_collection(HashMap<String, Object> data, String document_name, String collection) {
        return false;
    }

    @Override
    public HashMap<String, Object> get_open_range_by_day(String username, String day) {
        return null;
    }

    @Override
    public HashMap<String, Object> get_open_range_by_full_date(String username, String date) {
        return null;
    }

    @Override
    public int getDayNumberNew(LocalDate date) {
        return 0;
    }
}
