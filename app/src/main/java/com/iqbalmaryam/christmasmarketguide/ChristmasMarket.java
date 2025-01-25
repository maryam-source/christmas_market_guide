package com.iqbalmaryam.christmasmarketguide;

public class ChristmasMarket {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private int imageResId;

    // Constructor with all five parameters
    public ChristmasMarket(int id, String name, double latitude, double longitude, int imageResId) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageResId = imageResId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getImageResId() {
        return imageResId;
    }
}
