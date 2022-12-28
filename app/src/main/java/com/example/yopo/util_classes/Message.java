package com.example.yopo.util_classes;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {

    private String ques;
    private HashMap<String, Object> args;


    public Message(String ques, HashMap<String, Object> args) {
        this.ques = ques;
        this.args = args;
    }

    public HashMap<String, Object> get_args(){
        return this.args;
    }

    public String get_ques(){
        return this.ques;
    }

}
