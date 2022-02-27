package com.example.lifestyleapp;

public interface ButtonListener {

    void editProfileClick();
    void submitButtonClick(String firstName, String lastName, String gender,
                                  String city, String country, String weight, String height);
    void cameraButtonClick();
}
