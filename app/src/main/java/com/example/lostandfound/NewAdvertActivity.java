package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lostandfound.Data.DatabaseHelper;
import com.example.lostandfound.Model.Item;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class NewAdvertActivity extends AppCompatActivity {
    EditText na_Name,na_Phone,na_desc,na_Date,na_Location;
    Button na_Save;
    CheckBox na_Lost,na_Found;
    DatabaseHelper databaseHelper;
    String checkedStatus = "",item_name,item_phone,item_desc,item_date,item_location;

    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                na_Location.setText(location.getLatitude()+","+location.getLongitude());
            }
        };

        na_Name = findViewById(R.id.na_Name);
        na_Phone = findViewById(R.id.na_Phone);
        na_desc = findViewById(R.id.na_Desc);
        na_Date = findViewById(R.id.na_Date);
        na_Location = findViewById(R.id.na_Location);
        na_Save = findViewById(R.id.na_Save);
        na_Lost = findViewById(R.id.na_Lost);
        na_Found = findViewById(R.id.na_Found);
        databaseHelper = new DatabaseHelper(this);

        String API_KEY = "AIzaSyCaLq1bqdejYjeNYYoq50gGhVP2KOahVbw";
        Places.initialize(getApplicationContext(),API_KEY);

        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.frag_Auto_Complete);
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.ADDRESS, Place.Field.LAT_LNG));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(getApplicationContext(), "Error: "+status, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng latLng = place.getLatLng();
                na_Location.setText(latLng.longitude+","+latLng.latitude);
            }
        });
        Button btnGetCurrentLocation = findViewById(R.id.btnGetCurrentLocation);
        btnGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewAdvertActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }else{
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
                }
            }
        });
        na_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item_name = na_Name.getText().toString();
                item_location = na_Location.getText().toString();
                item_date = na_Date.getText().toString();
                item_desc = na_desc.getText().toString();
                item_phone = na_Phone.getText().toString();
                Item i = new Item();
                i.setDate(item_date);
                i.setDescription(item_desc);
                i.setLocation(item_location);
                i.setName(item_name);
                i.setPhone(item_phone);
                i.setLost(checkedStatus);
                databaseHelper.insertItem(i);
                finish();
            }
        });

    }
    public void onCheckBoxClicked(View v){
        Boolean checked =((CheckBox) v).isChecked();

        switch(v.getId()) {
            case R.id.na_Lost:
                if (checked){
                    checkedStatus="lost";
                    ((CheckBox)findViewById(R.id.na_Found)).setChecked(false);}
                break;
            case R.id.na_Found:
                if (checked){
                    checkedStatus="found";
                    ((CheckBox)findViewById(R.id.na_Lost)).setChecked(false);}
                break;
        }

    }
}