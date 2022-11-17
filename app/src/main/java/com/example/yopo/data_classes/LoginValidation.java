package com.example.yopo.data_classes;

public class LoginValidation {

    private String username, password;
    private boolean error;
    private String error_string;

    public LoginValidation(String username, String password){
        // fill values
        this.username = username;
        this.password = password;

        this.error = false;
        this.error_string = "";
    }

    public boolean is_valid(){
        // TODO add validation using firebase

        if (this.username.isEmpty()){
            this.error = true;
            this.error_string = "Please enter username";
        }
        else if (this.password.isEmpty()){
            this.error = true;
            this.error_string = "Please enter password";
        }
        return !this.error;
    }

    public String get_error_string() {return this.error_string; }


}
