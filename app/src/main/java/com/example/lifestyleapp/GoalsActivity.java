package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GoalsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText height, weight, age, amount;
    private TextView bmr, calorieEstimate;
    private Spinner goalSelect;
    private String gender, activityLevel, goal;
    private RadioGroup genderSelect, activeSelect;
    private RadioButton genderRadio, activeRadio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmr);

        //Radio button groups
        activeSelect = findViewById(R.id.activeButtonGroup);
        genderSelect = findViewById(R.id.maleFemaleButtonGroup);

        //Set up the spinner.
        goalSelect = findViewById(R.id.goalSpinner);
        goalSelect.setOnItemSelectedListener(this);
        List<String> options = new ArrayList<String>();
        options.add("Lose");
        options.add("Maintain");
        options.add("Gain");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalSelect.setAdapter(dataAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        this.goal = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        this.goal = null;
    }

    public void calculateBMR(){

    }
}
