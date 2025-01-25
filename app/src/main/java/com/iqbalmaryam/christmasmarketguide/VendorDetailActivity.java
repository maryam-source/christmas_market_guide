package com.iqbalmaryam.christmasmarketguide;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class VendorDetailActivity extends AppCompatActivity {

    private ArrayList<Vendor> vendorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_detail);

        vendorList = new ArrayList<>();

        // Adding vendors with all 5 parameters: name, description, image, latitude, and longitude 51.0537084956187, 13.733986760472384
        vendorList.add(new Vendor("Vendor 1", "Category 1", "Address 1", "Description 1", 51.05370, 13.73398));
        vendorList.add(new Vendor("Vendor 2", "Category 2", "Address 2", "Description 2", 51.06337, 13.7339));
        vendorList.add(new Vendor("Vendor 3", "Category 3", "Address 3", "Description 3", 51.07337, 13.7339));


        // Do something with the vendorList, such as displaying it in a ListView or RecyclerView
    }
}
