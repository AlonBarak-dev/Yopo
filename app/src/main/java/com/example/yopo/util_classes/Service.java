package com.example.yopo.util_classes;

public class Service {
    private String service;
    private float price;


    public Service(String service, float price) {
        this.service = service;
        this.price = price;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
