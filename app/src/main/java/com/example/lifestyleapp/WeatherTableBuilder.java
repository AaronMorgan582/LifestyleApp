package com.example.lifestyleapp;

public class WeatherTableBuilder {
    private String location;
    private String weatherJson;

    public WeatherTableBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public WeatherTableBuilder setWeatherJson(String weatherJson) {
        this.weatherJson = weatherJson;
        return this;
    }

    public com.example.lifestyleapp.WeatherTable createWeatherTable() {
        return new com.example.lifestyleapp.WeatherTable(location, weatherJson);
    }
}