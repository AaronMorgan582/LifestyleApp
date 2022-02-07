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

import android.Manifest;
import android.content.Intent;
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
    private Button submitButton, cameraButton;
    private EditText firstNameView, lastNameView, cityView, countryView, heightView, weightView, genderView;
    private String firstName, lastName, city, country, gender, height, weight;
    private Spinner spDropdown;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView profPicImageView;

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
                /*
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager())!=null){
                    activityResultLauncher.launch(cameraIntent);
                }
                else{
                    Toast.makeText(RegisterUserActivity.this, "Not able to open camera", Toast.LENGTH_SHORT).show();
                }
                /
                 */
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

            startActivity(displayUserInfoIntent);
        }
        else{
            Toast.makeText(this, "Enter all required parameters", Toast.LENGTH_SHORT).show();
        }
    }

    public void getProfilePicture(){

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
}