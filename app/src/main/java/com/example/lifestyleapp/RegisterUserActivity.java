package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class RegisterUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button submitButton;
    private EditText firstNameView, lastNameView, cityView, countryView;
    private String firstName, lastName, city, country;
    private Spinner spDropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Submit button
        setContentView(R.layout.activity_register_user);
        submitButton = (Button) findViewById(R.id.button);

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


        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        String city = cityView.getText().toString();
        String country = countryView.getText().toString();

        // First attempt at creating some error checking
        // Will need to be more robust later on
        if(checkInput(firstName, lastName, city, country)){
            Intent displayUserInfoIntent = new Intent(this, DisplayUserInfoActivity.class);
            displayUserInfoIntent.putExtra("USER_FIRST_NAME", firstName);
            displayUserInfoIntent.putExtra("USER_LAST_NAME", lastName);
            displayUserInfoIntent.putExtra("USER_CITY", city);
            displayUserInfoIntent.putExtra("USER_COUNTRY", country);
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}