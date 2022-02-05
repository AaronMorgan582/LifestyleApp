package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DisplayUserInfoActivity extends AppCompatActivity {

    private TextView firstNameDisplay, lastNameDisplay, height, weight, city, country, sex;
    private ImageView userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_info);
        Intent receivedIntent = getIntent();
        String receivedFirstName = receivedIntent.getStringExtra("USER_FIRST_NAME");
        String receivedLastName = receivedIntent.getStringExtra("USER_LAST_NAME");
        String receivedCity = receivedIntent.getStringExtra("USER_CITY");
        String receivedCountry = receivedIntent.getStringExtra("USER_COUNTRY");
        String receivedGender = receivedIntent.getStringExtra("USER_GENDER");
        String receivedWeight = receivedIntent.getStringExtra("USER_WEIGHT");
        String receivedHeight = receivedIntent.getStringExtra("USER_HEIGHT");

        this.firstNameDisplay = (TextView) findViewById(R.id.displayFirstName);
        this.lastNameDisplay = (TextView) findViewById(R.id.displayLastName);
        this.city = (TextView) findViewById(R.id.displayCity);
        this.country = (TextView) findViewById(R.id.displayCountry);
        this.sex = (TextView) findViewById(R.id.displayGender);
        this.height = (TextView) findViewById(R.id.displayHeight);
        this.weight = (TextView) findViewById(R.id.displayWeight);


        this.firstNameDisplay.setText(receivedFirstName);
        this.lastNameDisplay.setText(receivedLastName);
        this.city.setText(receivedCity);
        this.country.setText(receivedCountry);
        this.sex.setText(receivedGender);
        this.height.setText(receivedHeight);
        this.weight.setText(receivedWeight);



    }
}