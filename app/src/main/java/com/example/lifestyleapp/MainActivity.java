package com.example.lifestyleapp;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    private Button mButtonCreateProfile, drawerActivityBtn;
    private ImageButton mButtonHikes, mButtonGoals;
    private FusedLocationProviderClient fusedLocationClient;
    private double locx = 0;
    private double locy = 0;


    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION, false);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            // Precise location access granted.
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            // Only approximate location access granted.
                        } else {
                            // No location access granted.
                        }
                    }
            );


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        locx = location.getLongitude();
                        locx = location.getLatitude();
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });

        drawerActivityBtn = findViewById(R.id.drawerActivity);
        drawerActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDrawerActivity();
            }
        });

        //Get the button
        mButtonHikes = (ImageButton)findViewById(R.id.imageButton3);
        mButtonHikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                //We have to grab the search term and construct a URI object from it.
                //We'll hardcode WEB's location here
                Uri searchUri = Uri.parse("geo:"+locx+","+locy+"?q=" + "hikes");

                //Create the implicit intent
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, searchUri);

                //If there's an activity associated with this intent, launch it
                if(mapIntent.resolveActivity(getPackageManager())!=null){
                    startActivity(mapIntent);
                }
            }
        });

        mButtonGoals = findViewById(R.id.imageButton4);
        mButtonGoals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                openFitnessGoals();
            }
        });


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
    public void goToDrawerActivity()
    {
        Intent drawerIntent = new Intent(this, DrawerActivity.class);
        startActivity(drawerIntent);
    }

    public void openFitnessGoals(){
        Intent goalsIntent = new Intent(this, GoalsActivity.class);
        startActivity(goalsIntent);
    }

}