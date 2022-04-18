package com.example.lifestyleapp;

import static com.example.lifestyleapp.HomeFragment.locx;
import static com.example.lifestyleapp.HomeFragment.locy;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import static com.example.lifestyleapp.HomeFragment.city;



public class WeatherFragment extends Fragment {


    TextView temp, humid, pres, place;
    private ImageView therm, hum;
    private Drawable therm_image;
    private Drawable hum_image;
    private com.example.lifestyleapp.WeatherViewModel mWeatherViewModel;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_weather, container, false);


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

        //Create the view model
        mWeatherViewModel = new ViewModelProvider(this).get(com.example.lifestyleapp.WeatherViewModel.class);

        //Set the observer
        (mWeatherViewModel.getData()).observe(this,nameObserver);

        String inputFromEt = city.toString().replace(' ','&');
        loadWeatherData(inputFromEt);



        return view;
    }


    void loadWeatherData(String location){
        //pass the location in to the view model
        mWeatherViewModel.setLocation(location);
    }

    //create an observer that watches the LiveData<WeatherData> object
    final Observer<WeatherData> nameObserver  = new Observer<com.example.lifestyleapp.WeatherData>() {
        @Override
        public void onChanged(@Nullable final com.example.lifestyleapp.WeatherData weatherData) {
            // Update the UI if this data variable changes
            if(weatherData!=null) {


                double tempvaluedouble = weatherData.getTemperature().getTemp();
                tempvaluedouble = (tempvaluedouble - 273.15) * 9/5 + 32;
                String tempvalue = Double.toString(tempvaluedouble).substring(0,5);

                temp.setText("Temperature : " + tempvalue + " F");
                humid.setText("Humidity : " + weatherData.getCurrentCondition().getHumidity() + "%");
                pres.setText("Pressure : " + weatherData.getCurrentCondition().getPressure() + " Pa");
                place.setText(city);
            }
        }
    };



}