package com.example.yopo.util_classes;

public class OpenRange {
    private int start_hour, end_hour;


    public OpenRange(int start_hour, int end_hour) {
        this.start_hour = start_hour;
        this.end_hour = end_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public int getStart_hour() {
        return start_hour;
    }

    public int getEnd_hour() {
        return end_hour;
    }
}
