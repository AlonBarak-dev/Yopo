package com.example.yopo.data_classes;

public class UserRegisterParser {
    private String username, password, first_name, last_name, email, birth_date, city, street, home_num, floor;

    public UserRegisterParser(String username, String password, String first_name, String last_name, String email, String birth_date, String city, String street, String home_num, String floor) {
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
    }

    public boolean is_valid() {
        // TODO finish this validation form

        return !username.isEmpty();
    }
}
