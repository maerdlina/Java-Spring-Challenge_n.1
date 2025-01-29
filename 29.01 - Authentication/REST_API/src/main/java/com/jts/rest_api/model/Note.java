package com.jts.rest_api.model;

public class Note {
    private String name;

    public Note() {}

    public Note(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
