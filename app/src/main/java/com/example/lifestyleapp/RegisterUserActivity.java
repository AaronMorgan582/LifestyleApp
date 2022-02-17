package com.example.lifestyleapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class RegisterUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button submitButton, cameraButton, saveUserButton;
    private EditText firstNameView, lastNameView, cityView, countryView, heightView, weightView, genderView;
    private String firstName, lastName, city, country, gender, height, weight;
    private Spinner spDropdown;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView profPicImageView;
    private Bitmap imageBitmap;

    ActivityResultLauncher<Intent> activityResultLauncher =  registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    profPicImageView.setImageBitmap(bitmap);
                }

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Submit button
        setContentView(R.layout.activity_register_user);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);

        this.submitButton = (Button) findViewById(R.id.button);
        this.cameraButton = (Button) findViewById(R.id.addPictureButton);
        this.profPicImageView = (ImageView) findViewById(R.id.profilePicView);
        saveUserButton = findViewById(R.id.saveTestUser);

        // Set up for gender selection and dropdown menu
        spDropdown = (Spinner) findViewById(R.id.spinnerGenderSelect);
        spDropdown.setOnItemSelectedListener(this);
        List<String> options = new ArrayList<String>();
        options.add("Male");
        options.add("Female");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDropdown.setAdapter(dataAdapter);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUserInfo();
            }
        });

        this.cameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
                else{
                    Toast.makeText(RegisterUserActivity.this, "Not able to open camera", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Method for testing if we can save a user locally so that
        // we can change the user's data at any given point.
        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTestUser();
            }
        });
    }
    /**
     * Temporary method to show a summary of the user information
     * in another activity
     * */
    public void showUserInfo(){

        firstNameView = (EditText) findViewById(R.id.editTextFirstName);
        lastNameView = (EditText) findViewById(R.id.editTextLastName);
        cityView = (EditText) findViewById(R.id.editTextCity);
        countryView = (EditText) findViewById(R.id.editTextCountry);
        this.heightView = (EditText) findViewById(R.id.editTextHeight);
        this.weightView = (EditText) findViewById(R.id.editTextWeight);

        this.firstName = firstNameView.getText().toString();
        this.lastName = lastNameView.getText().toString();
        this.city = cityView.getText().toString();
        this.country = countryView.getText().toString();
        this.height = this.heightView.getText().toString();
        this.weight = this.weightView.getText().toString();


        // First attempt at creating some error checking
        // Will need to be more robust later on
        if(checkInput(firstName, lastName, city, country)){
            Intent displayUserInfoIntent = new Intent(this, DisplayUserInfoActivity.class);
            displayUserInfoIntent.putExtra("USER_FIRST_NAME", this.firstName);
            displayUserInfoIntent.putExtra("USER_LAST_NAME", this.lastName);
            displayUserInfoIntent.putExtra("USER_CITY", this.city);
            displayUserInfoIntent.putExtra("USER_COUNTRY", this.country);
            displayUserInfoIntent.putExtra("USER_GENDER", this.gender);
            displayUserInfoIntent.putExtra("USER_WEIGHT", this.weight);
            displayUserInfoIntent.putExtra("USER_HEIGHT", this.height);
            displayUserInfoIntent.putExtra("USER_PIC", this.imageBitmap);

            startActivity(displayUserInfoIntent);
        }
        else{
            Toast.makeText(this, "Enter all required parameters", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkInput(String firstName, String lastName, String city, String country){

        if(firstName != " " && lastName != " " && city != " " && country != " "){
            return true;
        }
        return false;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        this.gender = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            this.imageBitmap = (Bitmap) extras.get("data");
            this.profPicImageView.setImageBitmap(this.imageBitmap);
        }
        else{
            Toast.makeText(this, "Image could not be saved", Toast.LENGTH_SHORT).show();

        }
    }

    private void saveTestUser(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "USER_TEST", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FIRST_NAME", "Ripley");
        editor.putString("CITY", "Olympia Colony");
        editor.putString("WEIGHT", "76");
        boolean success = editor.commit();
        if(success){
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DrawerActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }


    }

}