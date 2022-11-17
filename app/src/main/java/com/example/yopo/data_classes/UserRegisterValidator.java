package com.example.yopo.data_classes;

public class UserRegisterValidator {
    private String username, password, first_name, last_name, email, birth_date, city, street, home_num, floor, phone_number;
    private boolean error;
    private String error_string;

    public UserRegisterValidator(String username, String password, String first_name, String last_name, String email, String birth_date, String city, String street, String home_num, String floor, String phone_num) {
        // values to check
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.birth_date = birth_date;
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
        // TODO finish this validation form
        if (this.username.isEmpty()) {
            this.error = true;
            this.error_string = "Must fill username field!";
        }
        return !this.error;
    }

    public String get_error_string() {
        return this.error_string;
    }
}
