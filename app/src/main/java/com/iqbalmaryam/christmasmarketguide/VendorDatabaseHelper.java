package com.iqbalmaryam.christmasmarketguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class VendorDatabaseHelper {

    private SQLiteHelper dbHelper;

    public VendorDatabaseHelper(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    // Add a new vendor to the database
    public void addVendor(Vendor vendor, int marketId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", vendor.getName());
        values.put("category", vendor.getCategory());
        values.put("address", vendor.getAddress());
        values.put("description", vendor.getDescription());
        values.put("latitude", vendor.getLatitude());
        values.put("longitude", vendor.getLongitude());
        values.put("market_id", marketId);
        db.insert("vendors", null, values);
    }

    // Fetch all vendors for a specific market
    public List<Vendor> getVendorsForMarket(int marketId) {
        List<Vendor> vendors = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("vendors", null, "market_id = ?", new String[]{String.valueOf(marketId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                vendors.add(new Vendor(name, category, address, description, latitude, longitude));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return vendors;
    }

    // Fetch the market name by ID
    public String getMarketNameById(int marketId) {
        switch (marketId) {
            case 1:
                return "Striezelmarkt";
            case 2:
                return "Neustadt Market";
            case 3:
                return "Altmarkt Christmas Market";
            case 4:
                return "Frauenkirche Market";
            case 5:
                return "Hauptstrasse Market";
            default:
                return "Unknown Market";
        }
    }

    // Initialize sample vendors
    public void initializeSampleData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("vendors", null, null); // Clear existing data (optional)

        // Add sample vendors to the database
        addVendor(new Vendor("Glühwein Stand", "Food", "Market Square", "Delicious hot mulled wine.", 51.050, 13.737), 1);
        addVendor(new Vendor("Crafts Shop", "Handicrafts", "Main Street", "Handmade Christmas gifts.", 51.061, 13.754), 1);
        addVendor(new Vendor("Bakery", "Food", "Altmarkt", "Freshly baked Christmas cookies.", 51.048, 13.732), 2);
        addVendor(new Vendor("Candle Maker", "Handicrafts", "Frauenkirche Plaza", "Beautiful handcrafted candles.", 51.051, 13.741), 3);
        addVendor(new Vendor("Cheese Stall", "Food", "Hauptstrasse", "A variety of artisan cheeses.", 51.063, 13.739), 4);
    }
}
