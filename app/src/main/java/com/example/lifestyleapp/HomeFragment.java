package com.example.lifestyleapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
    import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {


    FusedLocationProviderClient client;
    TextView hikes,weather;
    public static double locx=0;
    public static double locy=0;
    public static String  city="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        hikes = view.findViewById(R.id.textView3);
        weather = view.findViewById(R.id.textView4);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }else{
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }

        Button hikeBtn = view.findViewById(R.id.card_hike_btn);
        Button weatherBtn = view.findViewById(R.id.card_weather_btn);
        hikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //We have to grab the search term and construct a URI object from it.

                Uri searchUri = Uri.parse("geo:"+locx+","+locy+"?q=" + "hikes");

                //Create the implicit intent
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);

                //If there's an activity associated with this intent, launch it
                if(mapIntent.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivity(mapIntent);
                }

            }
        });
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                //Go to weather fragment when button is clicked
                WeatherFragment fragment3 = new WeatherFragment();
                fragmentTransaction.replace(R.id.fragment_container, fragment3);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
if(requestCode==100&&(grantResults.length>0)&&(grantResults[0]+grantResults[1]==PackageManager.PERMISSION_GRANTED)){
    getCurrentLocation();
}else{
    Toast.makeText(getActivity(),"Permission denied",Toast.LENGTH_SHORT).show();
}
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
    @Override
    public void onComplete(@NonNull Task<Location> task) {
        Location location = task.getResult();
        if (location != null) {
            locx = location.getLatitude();
            locy = location.getLongitude();
            //Send request to API to get trails
            String URL = "https://my-trails-backend.herokuapp.com/api/v1/trails-by-location?lat="+locx+"&lon="+locy;
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    try {

                        JSONArray array = response;

                        JSONObject jsonObject = (JSONObject) response.get(0);

                        hikes.setText("Recommended hike: "+jsonObject.optString("name")+"\n"+"Stars: "+jsonObject.optString("stars"));


                    } catch (Exception e) {
                        e.printStackTrace();
                        hikes.setText("Click the button below to find hikes near your location!");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    hikes.setText("Click the button below to find hikes near your location!");
                    error.printStackTrace();

                }
            });
            queue.add(request);

            //Send request to API to get weather data
            String URL2 = "https://api.openweathermap.org/data/2.5/weather?lat="+locx+"&lon="+locy+ "&appid=c1a1e58da8b47c04fbd012a495ba933a";
            RequestQueue queue2 = Volley.newRequestQueue(getActivity().getApplicationContext());
            JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, URL2, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        JSONArray ob = response.getJSONArray("weather");
                        JSONObject o = ob.optJSONObject(0);
                        city=response.getString("name");
                        JSONObject ob2 = response.getJSONObject("main");
                        String tempvalue = ob2.getString("temp");
                        double tempvaluedouble = Double.parseDouble(tempvalue);
                        tempvaluedouble = (tempvaluedouble - 273.15) * 9/5 + 32;
                        tempvalue = Double.toString(tempvaluedouble).substring(0,5);

                        weather.setText("Weather: "+ o.getString("main")+"-> "+o.getString("description")+"\n"+"Temperature: "+tempvalue);


                    } catch (Exception e) {
                        e.printStackTrace();
                        weather.setText("Error loading weather");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    error.printStackTrace();
                    weather.setText("Error loading weather");
                }
            });
            queue.add(request2);


        } else {
            LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10000).setFastestInterval(1000).setNumUpdates(1);
            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location1 = locationResult.getLastLocation();

                    locx = location1.getLatitude();
                    locy = location1.getLongitude();
                    //Send request to API to get data
                    String URL = "https://my-trails-backend.herokuapp.com/api/v1/trails-by-location?lat="+locx+"&lon="+locy;
                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            try {

                                JSONArray array = response;

                                JSONObject jsonObject = (JSONObject) response.get(0);

                                hikes.setText("Recommended hike: "+jsonObject.optString("name")+"\n"+"Stars: "+jsonObject.optString("stars"));


                            } catch (Exception e) {
                                e.printStackTrace();
                                hikes.setText("Click the button below to find hikes near your location!");
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            hikes.setText("Click the button below to find hikes near your location!");

                            error.printStackTrace();

                        }
                    });
                    queue.add(request);


                    //Send request to API to get data
                    String URL2 = "https://api.openweathermap.org/data/2.5/weather?lat="+locx+"&lon="+locy+ "&appid=c1a1e58da8b47c04fbd012a495ba933a";
                    RequestQueue queue2 = Volley.newRequestQueue(getActivity().getApplicationContext());
                    JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, URL2, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                JSONArray ob = response.getJSONArray("weather");
                                JSONObject o = ob.optJSONObject(0);

                                JSONObject ob2 = response.getJSONObject("main");
                                String tempvalue = ob2.getString("temp");
                                double tempvaluedouble = Double.parseDouble(tempvalue);
                                tempvaluedouble = (tempvaluedouble - 273.15) * 9/5 + 32;
                                tempvalue = Double.toString(tempvaluedouble).substring(0,5);

                                weather.setText("Weather: "+ o.getString("main")+"-> "+o.getString("description")+"\n"+"Temperature: "+tempvalue);


                            } catch (Exception e) {
                                e.printStackTrace();
                                weather.setText("Error loading weather");
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            error.printStackTrace();
                            weather.setText("Error loading weather");

                        }
                    });
                    queue.add(request2);
                }
            };
            client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }
});
        }else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }


    }


}

