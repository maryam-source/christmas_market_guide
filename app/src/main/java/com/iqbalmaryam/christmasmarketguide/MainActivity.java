package com.iqbalmaryam.christmasmarketguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<DataClass> dataList;
    MyAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        dataList = new ArrayList<>();
        initializeMarketData();

        adapter = new MyAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);
    }

    private void initializeMarketData() {
        dataList.add(new DataClass("Striezelmarkt",
                R.string.striezelmarkt_description,
                "Altmarkt",
                R.drawable.striezelmarkt));
        dataList.add(new DataClass("Neustadt Market",
                R.string.neustadt_market_description,
                "Hauptstrasse",
                R.drawable.neustadt_market));
        dataList.add(new DataClass("Altmarkt Christmas Market",
                R.string.altmarkt_description,
                "Altmarkt",
                R.drawable.altmarkt));
        dataList.add(new DataClass("Frauenkirche Market",
                R.string.frauenkirche_market_description,
                "Frauenkirche Plaza",
                R.drawable.frauenkirche));
        dataList.add(new DataClass("Hauptstrasse Market",
                R.string.hauptstrasse_description,
                "Hauptstrasse",
                R.drawable.hauptstrasse));
    }

    private void searchList(String text) {
        List<DataClass> filteredList = new ArrayList<>();
        for (DataClass data : dataList) {
            if (data.getDataTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(data);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No matches found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(filteredList);
        }
    }
}
