package com.example.lifestyleapp;

import android.content.Context;
import android.content.Intent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegisterUserFragment extends Fragment implements View.OnClickListener{
    private Button submitButton, cameraButton;
    private RadioGroup sexButtonGroup;
    private RadioButton sexRadioButton;
    private EditText firstNameView, lastNameView, cityView, countryView, heightView, weightView;
    private Context context;
    private ButtonListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_register_user, container, false);

        context = container.getContext();
        try{
            listener = (ButtonListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement ButtonListener interface.");
        }

        registerLayout(view);

        //If there are arguments from the Drawer Activity, use it to fill out the input fields.
        if (getArguments() != null) {
            User user_data = getArguments().getParcelable("user_data");
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:

                if(TextUtils.isEmpty(firstNameView.getText()) || TextUtils.isEmpty(lastNameView.getText())){
                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                }
                else{
                    gatherInputAndSubmit();
                }

                break;
            case R.id.addPictureButton:
                listener.cameraButtonClick();
                break;
        }
    }

    private void registerLayout(View view) {
        //Layout elements: Edit Text
        firstNameView = view.findViewById(R.id.editTextFirstName);
        lastNameView = view.findViewById(R.id.editTextLastName);
        cityView = view.findViewById(R.id.editTextCity);
        countryView = view.findViewById(R.id.editTextCountry);
        heightView = view.findViewById(R.id.editTextHeight);
        weightView = view.findViewById(R.id.editTextWeight);

        //Layout elements: Buttons
        submitButton = view.findViewById(R.id.button);
        cameraButton = view.findViewById(R.id.addPictureButton);
        submitButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        //Radio Group
        sexButtonGroup = view.findViewById(R.id.sexRadioGroup);

        //Get the selection from the radio group.
        int selected_sex = sexButtonGroup.getCheckedRadioButtonId();

        //Match the selection up with the appropriate button it's paired with.
        sexRadioButton = view.findViewById(selected_sex);
    }

    private void gatherInputAndSubmit() {
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        String sex = sexRadioButton.getText().toString();
        String city = cityView.getText().toString();
        String country = countryView.getText().toString();
        String weight = weightView.getText().toString();
        String height = heightView.getText().toString();
        listener.submitButtonClick(firstName, lastName, sex, city, country, weight, height);
    }
}
