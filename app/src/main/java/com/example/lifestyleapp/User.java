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

    //This is the string that is associated with finding the user's picture they took with the
    //camera. It's defaulted to blank because it's not necessary to fill in.
    private String fileName = "";

    public User(String firstName, String lastName, String gender,
                String city, String country, String weight, String height){

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.city = city;
        this.country = country;
        this.weight = weight;
        this.height = height;

        //Note: The fileName is not set in the constructor because it makes it easier to pass
        //around with the fragments - In the current setup, the Register User Fragment is
        //responsible for generating all of this info, but the Drawer Activity is responsible
        //for generating the string for the fileName.
    }

    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        gender = in.readString();
        city = in.readString();
        country = in.readString();
        weight = in.readString();
        height = in.readString();
        fileName = in.readString();
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
    public String getImageFileName() { return this.fileName; }
    public void setImageFileName(String file) { this.fileName = file; }

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
        parcel.writeString(fileName);
    }
}
