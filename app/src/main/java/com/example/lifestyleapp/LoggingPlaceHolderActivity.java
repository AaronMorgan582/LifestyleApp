package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoggingPlaceHolderActivity extends AppCompatActivity {

    private Button logIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_place_holder);
        logIn = findViewById(R.id.login_btn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationView();
            }
        });
    }
    public void openRegistrationView(){
        Intent intent = new Intent(this, DrawerActivity.class);
        startActivity(intent);
    }
}