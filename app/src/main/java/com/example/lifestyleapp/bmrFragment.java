package com.example.lifestyleapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class bmrFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText height, weight, age, amount;
    private TextView tvBMR, tvCalories;
    private Button bmr_calculate, calories_calculate;
    private Spinner goalSelect;
    private String goal, heightString, weightString, ageString, amountString;
    private RadioGroup genderSelect, activeSelect;
    private RadioButton genderRadio, activeRadio;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // User input
        view = inflater.inflate(R.layout.fragment_bmr, container, false);
        height = view.findViewById(R.id.fr_goalsEditTextHeight);
        weight = view.findViewById((R.id.fr_goalsEditTextWeight));
        age = view.findViewById(R.id.fr_goalsEditTextAge);
        amount = view.findViewById(R.id.fr_goalsEditTextPounds);

        //Text Views
        tvBMR = view.findViewById(R.id.fr_tvBmrCalculation);
        tvCalories = view.findViewById(R.id.fr_tvCalories);

        //Radio button groups
        activeSelect = view.findViewById(R.id.fr_activeButtonGroup);
        genderSelect = view.findViewById(R.id.fr_maleFemaleButtonGroup);

        //Set up the spinner.
        goalSelect = view.findViewById(R.id.fr_goalSpinner);
        goalSelect.setOnItemSelectedListener(this);
        List<String> options = new ArrayList<String>();
        options.add("Lose");
        options.add("Maintain");
        options.add("Gain");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalSelect.setAdapter(dataAdapter);

        //Buttons
        bmr_calculate = view.findViewById(R.id.fr_buttonBMR);
        calories_calculate = view.findViewById(R.id.fr_buttonCalorieEstimate);
        bmr_calculate.setOnClickListener(this);
        calories_calculate.setOnClickListener(this);

        if(getArguments() != null){
            User user_data = getArguments().getParcelable("user_data");
            if(!user_data.getHeight().matches("")){
                height.setText(user_data.getHeight());
            }
            if(!user_data.getWeight().matches("")){
                weight.setText(user_data.getWeight());
            }
        }

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
        this.goal = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {this.goal = null;}


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fr_buttonBMR:{
                int bmr = (int) calculateBMR();
                tvBMR.setText(bmr + " daily calories.");
                break;
            }
            case R.id.fr_buttonCalorieEstimate:{
                if(checkBMRInput()){
                    int calorieEstimate = calculateCalories();

                    if( (calorieEstimate < 1200 && genderRadio.getText().toString().matches("Male") )  ||
                            (calorieEstimate < 1100 && genderRadio.getText().toString().matches("Female") )){
                        Toast.makeText(getActivity(), "Calorie intake may be too low.", Toast.LENGTH_SHORT).show();
                    }
                    tvCalories.setText(calorieEstimate + " daily calories.");
                }
                break;
            }
        }

    }
    private boolean checkBMRInput(){
        heightString = height.getText().toString();
        weightString = weight.getText().toString();
        ageString = age.getText().toString();

        int gender = genderSelect.getCheckedRadioButtonId();
        int activityLevel = activeSelect.getCheckedRadioButtonId();

        if(heightString.matches("") || weightString.matches("") || ageString.matches("")
                || gender < 0 || activityLevel < 0){

            Toast.makeText(getActivity(), "Please enter all of the above information.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private double calculateBMR(){

        if(checkBMRInput()){
            double inches = Integer.parseInt(height.getText().toString());
            double pounds = Integer.parseInt(weight.getText().toString());
            double years = Integer.parseInt(age.getText().toString());

            int gender = genderSelect.getCheckedRadioButtonId();
            int activityLevel = activeSelect.getCheckedRadioButtonId();

            genderRadio = view.findViewById(gender);
            activeRadio = view.findViewById(activityLevel);

            if(genderRadio.getText().toString().matches("Male")){
                double value = 66 + (6.3 * pounds) + (12.9 * inches) - (6.8 * years);
                if(activeRadio.getText().toString().matches("Active")){
                    value = value * 1.55;
                }
                else if(activeRadio.getText().toString().matches("Sedentary")){
                    value = value * 1.2;
                }

                return value;
            }
            else if(genderRadio.getText().toString().matches("Female")){
                double value = 655 + (4.3 * pounds) + (4.7 * inches) - (4.7 * years);
                if(activeRadio.getText().toString().matches("Active")){
                    value = value * 1.55;
                }
                else if(activeRadio.getText().toString().matches("Sedentary")){
                    value = value * 1.2;
                }

                return value;
            }
        }

        return 0;
    }

    private int calculateCalories(){
        amountString = amount.getText().toString();

        if(amountString.matches("") && ( goal.matches("Lose") || goal.matches("Gain"))) {
            Toast.makeText(getActivity(), "Please enter a goal amount.", Toast.LENGTH_SHORT).show();
            return 0;
        }
        else if(goal.matches("Maintain")){
            return (int) calculateBMR();
        }
        else if(Double.parseDouble(amountString) > 2){
            Toast.makeText(getActivity(), "A value higher than 2 may be unhealthy.", Toast.LENGTH_SHORT).show();
        }

        if(goal.matches("Lose")){
            int weeklyBMRValue = (int) calculateBMR();
            double deficit = Double.parseDouble(amountString) * 500;
            return weeklyBMRValue - (int) deficit;
        }
        else if(goal.matches("Gain")){
            int weeklyBMRValue = (int) calculateBMR();
            double deficit = Double.parseDouble(amountString) * 500;
            return weeklyBMRValue + (int) deficit;
        }

        return 0;
    }
}