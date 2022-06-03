package com.example.nearby;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FetchData extends AsyncTask<Object,String ,String> {
    //Defining the variables
    String googleNearByPlaceData;
    GoogleMap googleMap;
    String url;

    @Override
    protected void onPostExecute(String s) {
       try {
           //Parsing the Json data with the help of Volley
           //Creating the JSON object
           JSONObject jsonObject = new JSONObject(s);
           //Creating the JSON Array
           JSONArray jsonArray = jsonObject.getJSONArray("results");
           //creating a for loop to get the data from the array
           for(int i = 0; i< jsonArray.length();i++){
               JSONObject jsonObject1 = jsonArray.getJSONObject(i);
               JSONObject getLocation = jsonObject1.getJSONObject("geometry")
                       .getJSONObject("location");
               String lat = getLocation.getString("lat");
               String lng = getLocation.getString("lng");

               JSONObject getName = jsonArray.getJSONObject(i);
               // getting the name of the location
               String name = getName.getString("name");
               //Setting the location
               LatLng latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
               //Setting the marker on the locations
               MarkerOptions markerOptions = new MarkerOptions();
               markerOptions.title(name);
               markerOptions.position(latLng);
               googleMap.addMarker(markerOptions);
               googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }
    }

    //used to perform the background operations
    @Override
    protected String doInBackground(Object... objects) {
        try {
            //Retrieve the data from the url
            googleMap=(GoogleMap) objects[0];
            url=(String) objects[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            googleNearByPlaceData = downloadUrl.retrieveUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Returning the google nearby place data
        return googleNearByPlaceData;
    }
}
