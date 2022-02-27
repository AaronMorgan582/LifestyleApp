package com.example.lifestyleapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class bmiFragment extends Fragment implements View.OnClickListener{

    private EditText height, height2, weight;
    private TextView tvBMI;
    private Button bmi_calculate;
    private String heightString, height2String, weightString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bmi, container, false);
        height = view.findViewById(R.id.fr_bmiEditTextHeight);
        height2 = view.findViewById((R.id.fr_bmiEditTextHeight2));
        weight = view.findViewById(R.id.fr_bmiEditTextWeight);
        try {
            String[] split = RegisterUserActivity.heightGlobal.split(" ");
            height.setText(split[0]);
            height2.setText(split[1]);
            weight.setText(RegisterUserActivity.weightGlobal);
        }catch(Exception e){

        }
        tvBMI = view.findViewById(R.id.fr_bmiCalculation);
        bmi_calculate = view.findViewById(R.id.fr_buttonBMI);
        bmi_calculate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(@NonNull View view) {
        switch(view.getId()){
            case R.id.fr_buttonBMI:{
                double bmi = calculateBMI();
                int scale = (int) Math.pow(10, 1);
                bmi = (double) Math.round(bmi * scale) / scale;
                tvBMI.setText(bmi+ " ");
                break;
            }
        }
    }
    public boolean checkBMIInput(){
        heightString = height.getText().toString();
        height2String = height2.getText().toString();
        weightString = weight.getText().toString();

        if(heightString.matches("") || height2String.matches("") || weightString.matches("")){
            Toast.makeText(getActivity(), "Please enter all of the above information.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private double calculateBMI(){

        if(checkBMIInput()){
            double inches = Integer.parseInt(height.getText().toString());
            double pounds = Integer.parseInt(height2.getText().toString());
            double years = Integer.parseInt(weight.getText().toString());
            double value = 0;
            double height = inches*12 + pounds;
            value = (years/height/height)*703;
            return value;

        }

        return 0;
    }
}