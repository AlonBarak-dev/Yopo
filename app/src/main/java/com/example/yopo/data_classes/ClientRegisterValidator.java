package com.example.yopo.data_classes;

public class ClientRegisterValidator {
    private String username, password, first_name, last_name, email, birth_date, city, street, home_num, floor, phone_number;
    private boolean error;
    private String error_string;

    public ClientRegisterValidator(String username, String password, String first_name, String last_name, String email, String birth_date, String city, String street, String home_num, String floor, String phone_num) {
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
        // TODO update validation
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
        if (this.first_name.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter first name!";
            return false;
        }
        if (this.last_name.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter last name!";
            return false;
        }
        if (this.birth_date.isEmpty()) {
            this.error = true;
            this.error_string = "Must enter valid birth date!";
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
