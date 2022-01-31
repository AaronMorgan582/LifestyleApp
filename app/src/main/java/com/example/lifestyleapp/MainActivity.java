package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    private Button mButtonCreateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonCreateProfile = (Button)findViewById(R.id.button2);
        mButtonCreateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileCreate();
            }
        });
    }

    public void openProfileCreate() {
        Intent profileIntent = new Intent(this, RegisterUserActivity.class);
        startActivity(profileIntent);

    }
}