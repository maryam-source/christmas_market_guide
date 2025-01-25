package com.iqbalmaryam.christmasmarketguide;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2; // Incremented version
    private static final String DATABASE_NAME = "vendorsDB";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VENDORS_TABLE = "CREATE TABLE vendors (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "category TEXT, " +
                "address TEXT, " +
                "description TEXT, " +
                "latitude REAL, " +
                "longitude REAL, " +
                "market_id INTEGER)"; // Added market_id
        db.execSQL(CREATE_VENDORS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE vendors ADD COLUMN market_id INTEGER");
        }
    }
}
