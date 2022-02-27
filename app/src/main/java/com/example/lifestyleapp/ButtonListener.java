package com.example.lifestyleapp;

public interface ButtonListener {

    public void editProfileClick();
    public void submitButtonClick(String firstName, String lastName, String gender,
                                  String city, String country, String weight, String height);
    public void cameraButtonClick();
}
