package com.example.lifestyleapp;

import org.json.JSONException;
import org.json.JSONObject;

//Declare methods as static. We don't want to create objects of this class.
public class JSONWeatherUtils {
    public static com.example.lifestyleapp.WeatherData getWeatherData(String data) throws JSONException{
        com.example.lifestyleapp.WeatherData weatherData = new com.example.lifestyleapp.WeatherData();

        //Start parsing JSON data
        JSONObject jsonObject = new JSONObject(data); //Must throw JSONException

        com.example.lifestyleapp.WeatherData.CurrentCondition currentCondition = weatherData.getCurrentCondition();
        JSONObject jsonMain = jsonObject.getJSONObject("main");
        currentCondition.setHumidity(jsonMain.getInt("humidity"));
        currentCondition.setPressure(jsonMain.getInt("pressure"));
        weatherData.setCurrentCondition(currentCondition);

        //Get the temperature, wind and cloud data.
        com.example.lifestyleapp.WeatherData.Temperature temperature = weatherData.getTemperature();
        com.example.lifestyleapp.WeatherData.Wind wind = weatherData.getWind();
        com.example.lifestyleapp.WeatherData.Clouds clouds = weatherData.getClouds();
        temperature.setMaxTemp(jsonMain.getDouble("temp_max"));
        temperature.setMinTemp(jsonMain.getDouble("temp_min"));
        temperature.setTemp(jsonMain.getDouble("temp"));
        weatherData.setTemperature(temperature);

        return weatherData;
    }
}