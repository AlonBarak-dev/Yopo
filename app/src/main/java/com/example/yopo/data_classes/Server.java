package com.example.yopo.data_classes;

import android.util.Log;
import android.util.Patterns;

import com.example.yopo.interfaces.IServer;

import java.time.DayOfWeek;
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
    private HashMap<Integer, String> day_of_week;


    private Server(){
        try {
            this.socket = new Socket("127.0.0.1", 2000);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.day_of_week = new HashMap<Integer, String>();
            this.day_of_week.put(1, "Monday");
            this.day_of_week.put(2, "Tuesday");
            this.day_of_week.put(3, "Wednesday");
            this.day_of_week.put(4, "Thursday");
            this.day_of_week.put(5, "Friday");
            this.day_of_week.put(6, "Saturday");
            this.day_of_week.put(7, "Sunday");
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
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("Business username", username);
        args.put("Date", Date);
        args.put("isClient", isClient);
        Message msg = new Message("Get Appointment info", args);
        List<HashMap<String, Object>> result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (List<HashMap<String, Object>>) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
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
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("service id", service_id);
        Message msg = new Message("Get Service", args);
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
    public List<HashMap<String, Object>> get_services(String username) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("Business username", username);
        Message msg = new Message("Get Services", args);
        List<HashMap<String, Object>> result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (List<HashMap<String, Object>>) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int count_appointments_on_date(String username, String Date, boolean isClient) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("Business username", username);
        args.put("Date", Date);
        args.put("isClient", isClient);
        Message msg = new Message("Count Appointments on date", args);
        int result = 0;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (Integer) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<HashMap<String, Object>> get_all_businesses() {
        HashMap<String, Object> args = new HashMap<String, Object>();
        Message msg = new Message("Get all businesses", args);
        List<HashMap<String, Object>> result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (List<HashMap<String, Object>>) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<HashMap<String, Object>> search_business(String businessName) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("Business username", businessName);
        Message msg = new Message("Search Business", args);
        List<HashMap<String, Object>> result = null;
        try{
            ObjectOutputStream stream_out = new ObjectOutputStream(this.socket.getOutputStream());
            stream_out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            ObjectInputStream stream_in = new ObjectInputStream(this.socket.getInputStream());
            result = (List<HashMap<String, Object>>) stream_in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean validate_email(String email_address) {
        return Patterns.EMAIL_ADDRESS.matcher(email_address).matches();
    }

    @Override
    public boolean add_to_collection(HashMap<String, Object> data, String document_name, String collection) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("Data", data);
        args.put("Dosument name", document_name);
        args.put("Collection name", collection);
        Message msg = new Message("Add to collection", args);
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
    public HashMap<String, Object> get_open_range_by_day(String username, String day) {
        HashMap<String, Object> args = new HashMap<String, Object>();
        args.put("Business username", username);
        args.put("Day", day);
        Message msg = new Message("Get open range by day", args);
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
    public HashMap<String, Object> get_open_range_by_full_date(String username, String date) {
        String[] date_parts = date.split("/");
        LocalDate date_obj = LocalDate.of(Integer.parseInt(date_parts[2]), Integer.parseInt(date_parts[1]), Integer.parseInt(date_parts[0]));
        int day_int = getDayNumberNew(date_obj);
        String day_str = this.day_of_week.get(day_int);
        return this.get_open_range_by_day(username, day_str);
    }

    @Override
    public int getDayNumberNew(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }
}
