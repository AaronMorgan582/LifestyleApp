package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lifestyleapp.RegisterUserActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BMIActivity extends AppCompatActivity implements  View.OnClickListener {

    private EditText height, height2, weight;
    private TextView tvBMI;
    private Button bmi_calculate;
    private String heightString, height2String, weightString;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);



        //User Input
        height = findViewById(R.id.bmiEditTextHeight);
        height2 = findViewById((R.id.bmiEditTextHeight2));
        weight = findViewById(R.id.bmiEditTextWeight);


        try {
            String[] split = RegisterUserActivity.heightGlobal.split(" ");
            height.setText(split[0]);
            height2.setText(split[1]);
            weight.setText(RegisterUserActivity.weightGlobal);
        }catch(Exception e){

        }

        //Text Views
        tvBMI = findViewById(R.id.bmiCalculation);




        //Buttons
        bmi_calculate = findViewById(R.id.buttonBMI);

        bmi_calculate.setOnClickListener(this);

    }




    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonBMI:{
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

            Toast.makeText(this, "Please enter all of the above information.", Toast.LENGTH_SHORT).show();
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
