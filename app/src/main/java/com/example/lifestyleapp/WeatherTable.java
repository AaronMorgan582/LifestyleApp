package com.example.lifestyleapp;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weather_table_old")
public class WeatherTable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "city_name")
    private String city;

    @NonNull
    @ColumnInfo(name = "x_cord")
    private String x;

    @NonNull
    @ColumnInfo(name = "y_cord")
    private String y;

    @NonNull
    @ColumnInfo(name = "temp_data")
    private String temp;

    @NonNull
    @ColumnInfo(name = "hum_data")
    private String hum;

    @NonNull
    @ColumnInfo(name = "pres_data")
    private String pres;


    public WeatherTable(@NonNull String city, @NonNull String x, @NonNull String y, @NonNull String temp,
                     @NonNull String hum, @NonNull String pres){

        this.city=city;
        this.x=x;
        this.y=y;
        this.temp=temp;
        this.hum=hum;
        this.pres=pres;
    }

    public String getCity(){
        return city;
    }

    public String getX(){
        return x;
    }

    public String getY(){
        return y;
    }

    public String getTemp(){
        return temp;
    }

    public String getHum(){
        return hum;
    }

    public String getPres(){
        return pres;
    }


    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public void setX(@NonNull String x) {
        this.x = x;
    }

    public void setY(@NonNull String y) {
        this.y = y;
    }

    public void setTemp(@NonNull String temp) {
        this.temp = temp;
    }

    public void setHum(@NonNull String hum) {
        this.hum = hum;
    }

    public void setPres(@NonNull String pres) {
        this.pres = pres;
    }

}
