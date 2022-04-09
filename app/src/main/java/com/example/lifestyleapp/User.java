package com.example.lifestyleapp;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User implements Parcelable {
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

    private boolean isRegistered;

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public boolean isRegistered() {
        return isRegistered;
    }


    //This is the string that is associated with finding the user's picture they took with the
    //camera. It's defaulted to blank because it's not necessary to fill in.
    private String imageFileName = "";

    public User(String firstName, String lastName, String age, String sex,
                String city, String country, String weight, String height){

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.city = city;
        this.country = country;
        this.weight = weight;
        this.height = height;

        //Note: The fileName is not set in the constructor because it makes it easier to pass
        //around with the fragments - In the current setup, the Register User Fragment is
        //responsible for generating all of this info, but the Drawer Activity is responsible
        //for generating the string for the fileName.
    }

    public String getFirstName(){ return this.firstName; }
    public String getLastName(){ return this.lastName; }
    public String getAge(){ return this.age; }
    public String getSex(){ return this.sex; }
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
    public String getImageFileName() { return this.imageFileName; }

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
    public void setImageFileName(String file) { this.imageFileName = file; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(sex);
        parcel.writeString(city);
        parcel.writeString(country);
        parcel.writeString(weight);
        parcel.writeString(height);
        parcel.writeString(imageFileName);
    }

    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        sex = in.readString();
        city = in.readString();
        country = in.readString();
        weight = in.readString();
        height = in.readString();
        imageFileName = in.readString();
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
}
