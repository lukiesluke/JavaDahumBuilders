package com.dahumbuilders.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dahumbuilders.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng dahumOffice = new LatLng(14.432342, 120.946600);
        LatLng prjAlfonzo = new LatLng(14.413131, 120.978068);
        LatLng prjMendoza = new LatLng(14.389020, 120.999265);
        LatLng prjTagaytay = new LatLng(14.141536, 120.999963);

        mMap.addMarker(new MarkerOptions().position(dahumOffice).title("Dahum Builders Office")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(prjAlfonzo).title("Alfonzo Village"));
        mMap.addMarker(new MarkerOptions().position(prjMendoza).title("Mendoza Village"));
        mMap.addMarker(new MarkerOptions().position(prjTagaytay).title("Greenfield Tagaytay"));

        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(dahumOffice, 13);
        mMap.animateCamera(location);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.xml.left_to_right, R.xml.right_to_left);
    }
}