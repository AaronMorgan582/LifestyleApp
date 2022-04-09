package com.example.lifestyleapp;

import static com.example.lifestyleapp.HomeFragment.locx;
import static com.example.lifestyleapp.HomeFragment.locy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class WeatherFragment extends Fragment {


    TextView temp, humid, pres, place;
    private ImageView therm, hum;
    private Drawable therm_image;
    private Drawable hum_image;
    FusedLocationProviderClient client;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        //Check permissions
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }

        temp = view.findViewById(R.id.temperature);
        humid = view.findViewById(R.id.humidity);
        pres = view.findViewById(R.id.pressure);
        place = view.findViewById(R.id.place);

        //Changes the color of the temperature and humidity icons to white.
        therm = view.findViewById(R.id.imageView);
        hum = view.findViewById(R.id.imageView2);
        therm_image = therm.getDrawable();
        therm_image.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);
        hum_image = hum.getDrawable();
        hum_image.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN);

        displayWeather();
        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
            getCurrentLocation();
        } else {
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        locx = location.getLatitude();
                        locy = location.getLongitude();
                        //Get data with x and y
                        displayWeather();


                    } else {
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();

                                locx = location1.getLatitude();
                                locy = location1.getLongitude();
                                //Get data with x and y
                                displayWeather();


                            }
                        };
                        client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        } else {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }


    }




            @RequiresApi(api = Build.VERSION_CODES.N)
            private void displayWeather(){

                //Send request to API
                String URL = "https://api.openweathermap.org/data/2.5/weather?lat="+locx+"&lon="+locy+ "&appid=c1a1e58da8b47c04fbd012a495ba933a";
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //Set text to API data
                            JSONObject ob = response.getJSONObject("main");
                                JSONObject pl = response.getJSONObject("sys");
                            //Get temp and convert from C to F
                            String tempvalue = ob.getString("temp");
                            double tempvaluedouble = Double.parseDouble(tempvalue);
                            tempvaluedouble = (tempvaluedouble - 273.15) * 9/5 + 32;
                            tempvalue = Double.toString(tempvaluedouble).substring(0,5);
                            String humidvalue = ob.getString("humidity");
                            String presvalue = ob.getString("pressure");
                            //Set all text
                            temp.setText("Temperature : " + tempvalue + " F");
                            humid.setText("Humidity : " + humidvalue + "%");
                            pres.setText("Pressure : " + presvalue + " Pa");
                            try{
                                //Try to get name of city and country
                                String name = response.getString("name");
                                String placestr =  name + ", "+pl.getString("country");
                                place.setText(placestr);
                            }catch(Exception e){
                                //If cant get name of city and country set text to x and y
                                place.setText(locx+", "+locy);
                            }
                        } catch (JSONException e) {
    e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        temp.setText("");
                        humid.setText("");
                        pres.setText("");
                        error.printStackTrace();

                    }
                });
                queue.add(request);

            }

}