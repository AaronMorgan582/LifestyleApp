package com.example.lifestyleapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
        tvBMI = view.findViewById(R.id.fr_bmiCalculation);
        bmi_calculate = view.findViewById(R.id.fr_buttonBMI);
        bmi_calculate.setOnClickListener(this);

        UsersViewModel model = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);
        model.getSelected().observe(getViewLifecycleOwner(), user -> {
            populateBMIInformation(user);
        });

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

    /**
     * Verifies the input needed to calculate BMI is present and of the correct type.
     */
    public boolean checkBMIInput(){
        heightString = height.getText().toString();
        height2String = height2.getText().toString();
        weightString = weight.getText().toString();

        if(heightString.matches("") || height2String.matches("") || weightString.matches("")){
            Toast.makeText(getActivity(), "Please enter all of the above information.", Toast.LENGTH_SHORT).show();
            return false;
        }

        try{
            Integer inches = Integer.parseInt(heightString);
            Integer feet = Integer.parseInt(height2String);
            Integer weight = Integer.parseInt(weightString);
        }catch (NumberFormatException e){
            Toast.makeText(getActivity(), R.string.invalid_bmi_input, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * The function used to calculate Body Mass Index.
     */
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

    /**
     * If available, fills in the information that this fragment uses if the user has previously
     * registered.
     * @param user the registered user
     */
    private void populateBMIInformation(User user){
        if(!user.getHeight().matches("")){
            try {
                String feet = String.valueOf(Integer.parseInt(user.getHeight()) / 12);
                String inches = String.valueOf(Integer.parseInt(user.getHeight()) % 12);
                height.setText(feet);
                height2.setText(inches);
            }catch(Exception e){
                height.setText("");
                height2.setText("");
            }
        }
        if(!user.getWeight().matches("")){
            weight.setText(user.getWeight());
        }
    }
}