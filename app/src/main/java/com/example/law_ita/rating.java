package com.example.law_ita;

public class rating {

    private String Name, Lastname, Comment;
    private float Rate;

    public rating() {
    }

    public rating(String name, String lastname, String comment, float rate) {
        Name = name;
        Lastname = lastname;
        Comment = comment;
        Rate = rate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public float getRate() {
        return Rate;
    }

    public void setRate(float rate) {
        Rate = rate;
    }
}

