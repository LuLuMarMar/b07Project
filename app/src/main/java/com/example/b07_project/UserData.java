package com.example.b07_project;

public class UserData {

    private String name;
    private int day;
    private int month;
    private int year;
    private int limit;

    public UserData() {
        // Default constructor required for calls to DataSnapshot.getValue(UserData.class)
    }

    public UserData(String name, int day, int month, int year, int limit) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getLimit() {
        return limit;
    }
}