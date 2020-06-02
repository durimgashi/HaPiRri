package com.fiek.hapirri;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import com.fiek.hapirri.model.Restaurant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        restaurant = getIntent().getParcelableExtra("restaurant");

        LatLng restLocation = new LatLng( restaurant.getLocation().getLatitude(), restaurant.getLocation().getLongitude() );
        mMap.addMarker(new MarkerOptions().position(restLocation).title("Marker in " + restaurant.getRestName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(restLocation));
    }
}
