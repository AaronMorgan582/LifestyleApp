package com.example.lifestyleapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String firstName;
    private String lastName;
    private String gender;
    private String city;
    private String country;
    private String weight;
    private String height;

    public User(String firstName, String lastName, String gender,
                String city, String country, String weight, String height){

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.city = city;
        this.country = country;
        this.weight = weight;
        this.height = height;
    }

    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        gender = in.readString();
        city = in.readString();
        country = in.readString();
        weight = in.readString();
        height = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFirstName(){
        return this.firstName;
    }
    public String getLastName(){
        return this.lastName;
    }
    public String getGender(){
        return this.gender;
    }
    public String getCity(){
        return this.city;
    }
    public String getCountry(){
        return this.country;
    }
    public String getWeight(){
        return this.weight;
    }
    public String getHeight(){
        return this.height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(gender);
        parcel.writeString(city);
        parcel.writeString(country);
        parcel.writeString(weight);
        parcel.writeString(height);
    }
}
