package com.jts.rest_api.model;

public class Card {
    private int id;
    private String imagePath;

    public Card(int id, String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }
}
