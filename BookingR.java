package com.example.newproject;

import android.os.Bundle;

public class BookingR {
    private String name;
    private String service;
    private String datetime;

    public BookingR(String name, String service, String datetime) {
        this.name = name;
        this.service = service;
        this.datetime = datetime;
    }

    public String getName() { return name; }
    public String getService() { return service; }
    public String getDatetime() { return datetime; }
}
