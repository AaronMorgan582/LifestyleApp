package com.example.lifestyleapp;

public class User {
    private String firstName;
    private String lastName;
    private String gender;
    private String city;
    private String country;
    private double weight;
    private double height;
    public User(String firstName, String lastName, String gender,
                String city, String country, double weight, double height){

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.city = city;
        this.country = country;
        this.weight = weight;
        this.height = height;
    }

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
    public double getWeight(){
        return this.weight;
    }
    public double getHeight(){
        return this.height;
    }
}
