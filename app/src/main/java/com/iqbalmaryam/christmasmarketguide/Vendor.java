package com.iqbalmaryam.christmasmarketguide;

public class Vendor {
    private String name;
    private String category;
    private String address;
    private String description;
    private double latitude;
    private double longitude;


    public Vendor(String name, String category, String address, String description, double latitude, double longitude) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
