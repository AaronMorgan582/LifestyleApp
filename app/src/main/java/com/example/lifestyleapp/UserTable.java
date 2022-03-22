package com.example.lifestyleapp;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserTable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "first_name")
    private String firstName;

    @NonNull
    @ColumnInfo(name = "last_name")
    private String lastName;

    @NonNull
    @ColumnInfo(name = "age")
    private String age;

    @NonNull
    @ColumnInfo(name = "height")
    private String height;

    @NonNull
    @ColumnInfo(name = "weight")
    private String weight;

    @NonNull
    @ColumnInfo(name = "sex")
    private String sex;

    @NonNull
    @ColumnInfo(name = "city")
    private String city;

    @NonNull
    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "user_image")
    private Bitmap userImage;

    public UserTable(@NonNull String firstName, @NonNull String lastName, @NonNull String age, @NonNull String height,
                     @NonNull String weight, @NonNull String sex, @NonNull String city, @NonNull String country){

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
        this.city = city;
        this.country = country;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getAge(){
        return age;
    }

    public String getHeight(){
        return height;
    }

    public String getWeight(){
        return weight;
    }

    public String getSex(){
        return sex;
    }

    public String getCity(){
        return city;
    }

    public String getCountry(){
        return country;
    }

    public Bitmap getUserImage(){
        return userImage;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    public void setAge(@NonNull String age) {
        this.age = age;
    }

    public void setHeight(@NonNull String height) {
        this.height = height;
    }

    public void setWeight(@NonNull String weight) {
        this.weight = weight;
    }

    public void setSex(@NonNull String sex) {
        this.sex = sex;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public void setCountry(@NonNull String country) {
        this.country = country;
    }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }
}
