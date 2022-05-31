package com.example.lostandfound;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.lostandfound.Data.DatabaseHelper;
import com.example.lostandfound.Model.Item;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.lostandfound.databinding.ActivityShowOnMapBinding;

import java.util.ArrayList;
import java.util.List;

public class ShowOnMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityShowOnMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShowOnMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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





        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<Item> items = databaseHelper.fetchAllItems();
        for(int i=0; i<items.size(); i++){

            String[] splitLoc = items.get(i).getLocation().split(",");
            try {

                LatLng temp = new LatLng(Double.parseDouble(splitLoc[0]),Double.parseDouble(splitLoc[1]));
                mMap.addMarker(new MarkerOptions()
                    .position(temp)
                    .title(items.get(i).getLost()+" "+items.get(i).getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(temp));

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Error!",Toast.LENGTH_LONG).show();
            }
        }
    }
}