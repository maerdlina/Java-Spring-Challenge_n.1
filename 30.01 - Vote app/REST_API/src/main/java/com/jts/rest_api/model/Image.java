package com.jts.rest_api.model;

public class Image {
    private String path;
    private int rating;

    public Image(String path) {
        this.path = path;
        this.rating = 0; // Начальный рейтинг
    }

    // Геттеры и сеттеры
    public String getPath() { return path; }
    public int getRating() { return rating; }
    public void incrementRating() { this.rating++; }
}
