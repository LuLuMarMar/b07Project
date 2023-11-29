package com.example.b07_project;

public class Feedback {
    private int rating;
    private String comment;
    private String name;

    public Feedback() {

    }

    public Feedback(int rating, String comment, String name) {
        this.rating = rating;
        this.comment = comment;
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    public String getName() { return name; }
}
