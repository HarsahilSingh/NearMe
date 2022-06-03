package com.example.nearby;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.nearby.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Defining the variables
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    //request code for location permission
    private  static final int Request_code = 101;
    private double lat,lng;
    ImageButton hospital,atm,restaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Fetching the id from the xml file
        hospital =  findViewById(R.id.hospital);
        atm = findViewById(R.id.atm);
        restaurant = findViewById(R.id.restaurant);

        //Initializing fused location provider client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //On click listener for the Hospital Button
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                //using string builder to set the url
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location="+lat + " ,"+lng);
                stringBuilder.append("&radius=10000");
                stringBuilder.append("&type=hospital");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=" +getResources().getString(R.string.google_maps_key));

                String url = stringBuilder.toString();
                Object dataFetch[] = new Object[2];
                dataFetch[0]=mMap;
                dataFetch[1]=url;
                // Creating the Fetch data object
                FetchData fetchData = new FetchData();
                //Executing the fetch data
                fetchData.execute(dataFetch);
            }
        });

        //On click listener for the ATM Button
        atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                //using string builder to set the url
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location="+lat + " ,"+lng);
                stringBuilder.append("&radius=1000");
                stringBuilder.append("&type=atm");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=" +getResources().getString(R.string.google_maps_key));

                String url = stringBuilder.toString();
                Object dataFetch[] = new Object[2];
                dataFetch[0]=mMap;
                dataFetch[1]=url;
                // Creating the Fetch data object
                FetchData fetchData = new FetchData();
                //Executing the fetch data
                fetchData.execute(dataFetch);
            }
        });

        //On click listener for the Restaurant Button
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.clear();
                //using string builder to set the url
                StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                stringBuilder.append("location="+lat + ","+lng);
                stringBuilder.append("&radius=1000");
                stringBuilder.append("&type=restaurant");
                stringBuilder.append("&sensor=true");
                stringBuilder.append("&key=" +getResources().getString(R.string.google_maps_key));

                String url = stringBuilder.toString();
                Object dataFetch[] = new Object[2];
                dataFetch[0]=mMap;
                dataFetch[1]=url;
                // Creating the Fetch data object
                FetchData fetchData = new FetchData();
                //Executing the fetch data
                fetchData.execute(dataFetch);
            }
        });
    }

    //Manipulates the map once available.
    //This callback is triggered when the map is ready to be used.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
     //Getting the current location of the device
     getCurrentLocation();
    }

    //Method to get the Current Location
    private void getCurrentLocation(){
        //Check for the location permission
        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //If the location is nor granted, request for the permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_code);
            return;
        }
        // Creating location request object
        LocationRequest locationRequest = LocationRequest.create();
        //Setting the interval for the location
        locationRequest.setInterval(60000);
        // Creating priority for the high accuracy
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //Setting the fastest interval for the location
        locationRequest.setFastestInterval(5000);
        //creating location call back object to recieve notification whenever the location is changed
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
               // Toast.makeText(getApplicationContext(), "Location result is"+locationResult, Toast.LENGTH_LONG).show();
                if (locationRequest == null){
                   // Toast.makeText(getApplicationContext(), "Current Location is null", Toast.LENGTH_LONG).show();
                    return;
                }
                //Getting the updated location
                for(Location location:locationResult.getLocations()){
                    if(location!=null){
                        //Toast.makeText(getApplicationContext(),"Current Location is" + location.getLongitude(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        };

        //Requesting the regular updates about the device location
        fusedLocationProviderClient.requestLocationUpdates
                (locationRequest,locationCallback,null);

        //Getting the last location of the device with the fused location provider client
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        //Adding the success listener
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //Checking whether the location is null or not
                if(location != null){
                    //if the location is not null get the latitude and longitude
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    LatLng latLng = new LatLng(lat,lng);
                    //Adding the marker to the map on the current location of the device
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                }
            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (Request_code){
            //if the permission is granted after the request is made then implementing the getcurrentlocation method
            case Request_code:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    getCurrentLocation();
                }
        }
    }
}