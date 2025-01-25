package com.iqbalmaryam.christmasmarketguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MarketDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_detail);

        ImageView marketHeaderImage = findViewById(R.id.marketHeaderImage);
        TextView marketInfoTextView = findViewById(R.id.marketInfoTextView);
        Button getDirectionsButton = findViewById(R.id.getDirectionsButton);

        Intent intent = getIntent();
        int marketId = intent.getIntExtra("marketId", -1);
        VendorDatabaseHelper dbHelper = new VendorDatabaseHelper(this);

        // Fetch market details
        String marketName = dbHelper.getMarketNameById(marketId);
        String description = getMarketDescriptionById(marketId);
        double[] marketCoordinates = getMarketCoordinatesById(marketId);

        // Update UI
        marketHeaderImage.setImageResource(getImageResourceByMarketId(marketId));
        marketInfoTextView.setText(description);

        // Handle "Get Directions" button click
        getDirectionsButton.setOnClickListener(v -> {
            if (marketCoordinates[0] != 0.0 && marketCoordinates[1] != 0.0) {
                String uri = String.format("google.navigation:q=%f,%f", marketCoordinates[0], marketCoordinates[1]);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    marketInfoTextView.setText("Google Maps is not installed on this device.");
                }
            } else {
                marketInfoTextView.setText("Coordinates for this market are not available.");
            }
        });
    }

    private int getImageResourceByMarketId(int marketId) {
        switch (marketId) {
            case 1:
                return R.drawable.striezelmarkt;
            case 2:
                return R.drawable.neustadt_market;
            case 3:
                return R.drawable.altmarkt;
            case 4:
                return R.drawable.frauenkirche;
            case 5:
                return R.drawable.hauptstrasse;
            default:
                return R.drawable.christmas_icon;
        }
    }

    private double[] getMarketCoordinatesById(int marketId) {
        switch (marketId) {
            case 1:
                return new double[]{51.050, 13.737}; // Striezelmarkt
            case 2:
                return new double[]{51.061, 13.754}; // Neustadt Market
            case 3:
                return new double[]{51.048, 13.732}; // Altmarkt Market
            case 4:
                return new double[]{51.051, 13.741}; // Frauenkirche Market
            case 5:
                return new double[]{51.063, 13.739}; // Hauptstrasse Market
            default:
                return new double[]{0.0, 0.0}; // Unknown market
        }
    }

    private String getMarketDescriptionById(int marketId) {
        switch (marketId) {
            case 1:
                return getString(R.string.striezelmarkt_description);
            case 2:
                return getString(R.string.neustadt_market_description);
            case 3:
                return getString(R.string.altmarkt_description);
            case 4:
                return getString(R.string.frauenkirche_market_description);
            case 5:
                return getString(R.string.hauptstrasse_description);
            default:
                return "No description available.";
        }
    }
}