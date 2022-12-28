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
    public BusinessRegisterValidator() {
        // values to check
        this.username = null;
        this.password = null;
        this.business_name = null;
        this.email = null;
        this.city = null;
        this.street = null;
        this.home_num = null;
        this.floor = null;
        this.phone_number = null;

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
        if (this.email.isEmpty() || !Database.getInstance().validate_email(this.email)) {
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

    public boolean price_list_is_valid(String service1, String price1, String service2, String price2,
                                       String service3, String price3, String service4, String price4, String service5, String price5) {

        if (service1.isEmpty()) {
            this.error_string = "Must at least 1 service!";
            return false;
        }
        if ((!service1.isEmpty() && price1.isEmpty()) ||(service1.isEmpty() && !price1.isEmpty())) {
            this.error_string = "Service must have a price!";
            return false;
        }
        if ((!service2.isEmpty() && price2.isEmpty()) ||(service2.isEmpty() && !price2.isEmpty())) {
            this.error_string = "Service must have a price!";
            return false;
        }
        if ((!service3.isEmpty() && price3.isEmpty()) ||(service3.isEmpty() && !price3.isEmpty())) {
            this.error_string = "Service must have a price!";
            return false;
        }
        if ((!service4.isEmpty() && price4.isEmpty()) ||(service4.isEmpty() && !price4.isEmpty())) {
            this.error_string = "Service must have a price!";
            return false;
        }
        if ((!service5.isEmpty() && price5.isEmpty()) ||(service5.isEmpty() && !price5.isEmpty())) {
            this.error_string = "Service must have a price!";
            return false;
        }
        return true;
    }


    public String get_error_string() {
        return this.error_string;
    }
}
