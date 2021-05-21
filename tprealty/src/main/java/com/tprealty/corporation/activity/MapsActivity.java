package com.tprealty.corporation.activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tprealty.corporation.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        LatLng tpCorpOffice = new LatLng(14.367562,120.911457);
        LatLng tpKawayanRaqzFaith = new LatLng(14.345385,120.881325);
        LatLng tpTagaytayRaqzSlab = new LatLng(14.152033,120.925852);
        LatLng tpGenCasDelaTorre = new LatLng(14.367522,120.909690);

        mMap.addMarker(new MarkerOptions().position(tpKawayanRaqzFaith).title("Raqz Faith"));
        mMap.addMarker(new MarkerOptions().position(tpTagaytayRaqzSlab).title("Raqz Love"));
        mMap.addMarker(new MarkerOptions().position(tpGenCasDelaTorre).title("Cas Dela Torre"));

        mMap.addMarker(new MarkerOptions().position(tpCorpOffice).title("TP Realty Corporation")).showInfoWindow();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(tpCorpOffice, 13);
        mMap.animateCamera(location);


    }
}