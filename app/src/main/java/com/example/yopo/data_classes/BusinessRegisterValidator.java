package com.example.yopo.data_classes;

import android.widget.EditText;

public class BusinessRegisterValidator {
    private String username, password, email, city, street, home_num, floor, phone_number, business_description, business_name;
    private String category, sub_category;
    private boolean error;
    private String error_string;

    public BusinessRegisterValidator(String username, String password, String email, String city, String street, String home_num, String floor, String phone_num, String business_name) {
        // values to check
        this.username = username;
        this.password = password;
        this.business_name = business_name;
        this.email = email;
        this.city = city;
        this.street = street;
        this.home_num = home_num;
        this.floor = floor;
        this.phone_number = phone_num;

        // for messaging
        this.error = false;
        this.error_string = "";
    }

    public boolean is_valid() {
        // TODO update validation
        if (this.business_name.isEmpty()){
            this.error = true;
            this.error_string = "Must enter Business Name!";
            return false;
        }
        if (this.username.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter username!";
            return false;
        }
        if (this.password.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter password!";
            return false;
        }
        if (this.email.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter valid email!";
            return false;
        }
        if (this.city.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter city!";
            return false;
        }
        if (this.street.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter street!";
            return false;
        }
        if (this.home_num.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter home number!";
            return false;
        }
        if (this.floor.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter floor!";
            return false;
        }
        if (this.phone_number.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter phone number!";
            return false;
        }
        return !this.error;
    }

    public String get_error_string() {
        return this.error_string;
    }
}
