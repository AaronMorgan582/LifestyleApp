package com.example.lifestyleapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<com.example.lifestyleapp.WeatherData> jsonData;
    private WeatherRepository mWeatherRepository;

    public WeatherViewModel(Application application){
        super(application);
        mWeatherRepository = WeatherRepository.getInstance(application);
        jsonData = mWeatherRepository.getData();
    }
    public void setLocation(String location){

        mWeatherRepository.setLocation(location);
    }

    public LiveData<com.example.lifestyleapp.WeatherData> getData(){
        return jsonData;
    }


}
