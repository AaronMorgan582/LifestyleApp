package com.example.lifestyleapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import static com.example.lifestyleapp.HomeFragment.locx;
import static com.example.lifestyleapp.HomeFragment.locy;

public class NetworkUtils {
    private static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static String APPIDQUERY = "&appid=";
    private static final String app_id="99ea8382701bd7481e5ea568772f739a";

    public static URL buildURLFromString(String location){
        URL myURL = null;
        try{
            myURL = new URL("https://api.openweathermap.org/data/2.5/weather?lat="+locx+"&lon="+locy+ "&appid=c1a1e58da8b47c04fbd012a495ba933a");
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return myURL;
    }

    public static String getDataFromURL(URL url) throws IOException{
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            //The scanner trick: search for the next "beginning" of the input stream
            //No need to user BufferedReader
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }
            else{
                return null;
            }

        }finally {
            urlConnection.disconnect();
        }
    }
}