package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DisplayUserInfoActivity extends AppCompatActivity {

    private TextView firstNameDisplay, lastNameDisplay, height, weight, city, country, sex;
    private Button editProfile;
    private ImageView userImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_info);
        this.editProfile = (Button) findViewById(R.id.buttonEditProfile);
        Intent receivedIntent = getIntent();
        String receivedFirstName = receivedIntent.getStringExtra("USER_FIRST_NAME");
        String receivedLastName = receivedIntent.getStringExtra("USER_LAST_NAME");
        String receivedCity = receivedIntent.getStringExtra("USER_CITY");
        String receivedCountry = receivedIntent.getStringExtra("USER_COUNTRY");
        String receivedGender = receivedIntent.getStringExtra("USER_GENDER");
        String receivedWeight = receivedIntent.getStringExtra("USER_WEIGHT");
        String receivedHeight = receivedIntent.getStringExtra("USER_HEIGHT");
        Bitmap receivedPic = receivedIntent.getParcelableExtra("USER_PIC");

        this.firstNameDisplay = (TextView) findViewById(R.id.displayFirstName);
        this.lastNameDisplay = (TextView) findViewById(R.id.displayLastName);
        this.city = (TextView) findViewById(R.id.displayCity);
        this.country = (TextView) findViewById(R.id.displayCountry);
        this.sex = (TextView) findViewById(R.id.displayGender);
        this.height = (TextView) findViewById(R.id.displayHeight);
        this.weight = (TextView) findViewById(R.id.displayWeight);
        this.userImage = (ImageView) findViewById(R.id.profilePicture);



        this.firstNameDisplay.setText(receivedFirstName);
        this.lastNameDisplay.setText(receivedLastName);
        this.city.setText(receivedCity);
        this.country.setText(receivedCountry);
        this.sex.setText(receivedGender);
        this.height.setText(receivedHeight);
        this.weight.setText(receivedWeight);
        this.userImage.setImageBitmap(receivedPic);

//        editProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                returnToRegisterUserDisplay();
//            }
//        });

    }

//    public void returnToRegisterUserDisplay(){
//        Intent intent = new Intent(this, RegisterUserActivity.class);
//        startActivity(intent);
//    }

}