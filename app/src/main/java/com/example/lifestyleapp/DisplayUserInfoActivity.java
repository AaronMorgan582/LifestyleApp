package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayUserInfoActivity extends AppCompatActivity {

    private TextView firstNameDisplay;
    private TextView lastNameDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_info);
        Intent receivedIntent = getIntent();
        String receivedFirstName = receivedIntent.getStringExtra("USER_FIRST_NAME");
        String receivedLastName = receivedIntent.getStringExtra("USER_LAST_NAME");
        firstNameDisplay = (TextView) findViewById(R.id.displayFirstName);
        lastNameDisplay = (TextView) findViewById(R.id.displayLastName);
        firstNameDisplay.setText(receivedFirstName);
        lastNameDisplay.setText(receivedLastName);
    }
}