package com.example.lifestyleapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegisterUserFragment extends Fragment implements View.OnClickListener {
    private Button submitButton, cameraButton;
    private EditText firstNameView, lastNameView, cityView, countryView, heightView, weightView;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_register_user, container, false);
        context = container.getContext();

        //Layout elements: Edit Text
        firstNameView = view.findViewById(R.id.editTextFirstName);
        lastNameView = view.findViewById(R.id.editTextLastName);
        cityView = view.findViewById(R.id.editTextCity);
        countryView = view.findViewById(R.id.editTextCountry);
        heightView = view.findViewById(R.id.editTextHeight);
        weightView = view.findViewById(R.id.bmiEditTextWeight);

        //Layout elements: Buttons
        submitButton = view.findViewById(R.id.button);
        cameraButton = view.findViewById(R.id.addPictureButton);
        submitButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case(R.id.button):{
                break;
            }
            case(R.id.addPictureButton):{
                break;
            }
        }
    }

    //TODO: Need to pass the first name back (maybe all info?) to the Drawer Activity on the "Submit" button call.
}
