package com.example.nearme;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//This class is used to retrieve data from the URl using HTTP URL connection and file handling methods
public class DownloadUrl {
    //Method to retrieve the URL
    public  String retrieveUrl(String url)throws IOException{
        //Defining the variables
        String urlData="";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream =null;
        try{
            URL getUrl = new URL(url);
            //Creating an http connection to communicate with the url
            httpURLConnection=(HttpURLConnection) getUrl.openConnection();
            httpURLConnection.connect();

            //Reading the data from the url
            inputStream=httpURLConnection.getInputStream();

            // Using buffered reader to read the Input stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";

            //Using while loop to read the data
            while((line = bufferedReader.readLine())!=null){
                //Adding the data to the String Buffer
                sb.append(line);
            }
            urlData = sb.toString();
            bufferedReader.close();
        }catch (Exception e){
            Log.d("Exception",e.toString());
        }finally {
            //Closing the input stream and disconnecting the Http connection
            inputStream.close();
            httpURLConnection.disconnect();
        }
        //Returning the data from the URL
        return urlData;
    }
}
