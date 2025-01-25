package com.iqbalmaryam.christmasmarketguide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.widget.Toast;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private GoogleMap gMap;
    private FusedLocationProviderClient fusedLocationClient;
    private boolean isLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        requestLocationPermission();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        // Apply custom map style
        try {
            boolean success = gMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
            if (!success) {
                Toast.makeText(this, "Failed to apply map style!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading map style: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Display markers for Christmas markets
        List<ChristmasMarket> markets = getChristmasMarkets();
        for (ChristmasMarket market : markets) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(market.getLatitude(), market.getLongitude()))
                    .title(market.getName());
            Marker marker = gMap.addMarker(markerOptions);
            if (marker != null) {
                marker.setTag(market.getId());
            }
        }

        // Handle marker click to open MarketDetailActivity
        gMap.setOnMarkerClickListener(marker -> {
            Integer marketId = (Integer) marker.getTag();
            if (marketId != null) {
                Intent intent = new Intent(MapsActivity.this, MarketDetailActivity.class);
                intent.putExtra("marketId", marketId);
                startActivity(intent);
            }
            return false;
        });

        // Move camera to Dresden as a default position
        LatLng dresden = new LatLng(51.050, 13.737);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dresden, 12));

        // Add user location if permission is granted
        if (isLocationPermissionGranted) {
            enableUserLocation();
        }
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            isLocationPermissionGranted = true;
        }
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            gMap.setMyLocationEnabled(true);
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    // Add a marker for the user's current location
                    LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                    gMap.addMarker(new MarkerOptions()
                            .position(userLatLng)
                            .title("You are here")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    // Move the camera to the user's location
                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 14));
                } else {
                    Toast.makeText(this, "Unable to fetch your location.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isLocationPermissionGranted = true;
                if (gMap != null) {
                    enableUserLocation();
                }
            } else {
                Toast.makeText(this, "Location permission is required to show your current location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<ChristmasMarket> getChristmasMarkets() {
        List<ChristmasMarket> markets = new ArrayList<>();
        markets.add(new ChristmasMarket(1, "Striezelmarkt", 51.050, 13.737, R.drawable.striezelmarkt));
        markets.add(new ChristmasMarket(2, "Neustadt Market", 51.061, 13.754, R.drawable.neustadt_market));
        markets.add(new ChristmasMarket(3, "Altmarkt Christmas Market", 51.048, 13.732, R.drawable.altmarkt));
        markets.add(new ChristmasMarket(4, "Frauenkirche Market", 51.051, 13.741, R.drawable.frauenkirche));
        markets.add(new ChristmasMarket(5, "Hauptstrasse Market", 51.063, 13.739, R.drawable.hauptstrasse));
        return markets;
    }
}
