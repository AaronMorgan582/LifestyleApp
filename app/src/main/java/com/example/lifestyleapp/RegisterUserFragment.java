package com.example.lifestyleapp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.FileNotFoundException;



public class RegisterUserFragment extends Fragment implements View.OnClickListener {

    private RadioGroup sexButtonGroup;
    private RadioButton sexRadioButton, maleRadioButton, femaleRadioButton;
    private EditText firstNameView, lastNameView, cityView, countryView, heightView, weightView;
    private ImageView profilePicture;
    private ButtonListener listener;
    private View view;

    // View model
    private UsersViewModel usersViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_register_user, container, false);

        //Ensure that the activity coupled with this fragment has the ButtonListener interface implemented.
        Context context = container.getContext();
        try {
            listener = (ButtonListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ButtonListener interface.");
        }

        //Get the elements from the layout.
        registerLayout(view);

        usersViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);
        usersViewModel.getSelected().observe(getViewLifecycleOwner(), user -> {
            autoFill(user);
        });

        //If there are arguments from the Drawer Activity, use it to fill out the input fields.

        /*
        if (getArguments() != null) {
            User user_data = getArguments().getParcelable("user_data");
            autoFill(user_data);
        }*/

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                if (TextUtils.isEmpty(firstNameView.getText()) || TextUtils.isEmpty(lastNameView.getText())) {
                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {

                    if(checkHeightAndWeightForDigits(heightView.getText().toString(), weightView.getText().toString()))
                        gatherInputAndSubmit();
                }
                break;
            case R.id.addPictureButton:
                listener.cameraButtonClick();
                break;
        }
    }

    private void autoFill(User user) {
        firstNameView.setText(user.getFirstName());
        lastNameView.setText(user.getLastName());
        cityView.setText(user.getCity());
        countryView.setText(user.getCountry());
        heightView.setText(user.getHeight());
        weightView.setText(user.getWeight());
        if (user.getGender().matches("Female")) {
            femaleRadioButton.setChecked(true);
        } else if (user.getGender().matches("Male")) {
            maleRadioButton.setChecked(true);
        }
        //It's possible that the image has not been set, so check to see if it's in the User class.
        if (!user.getImageFileName().matches("")) {
            //The try/catch block is mostly for openFileInput; it's possible the filepath doesn't exist
            //even if it is in the User class.
            try {
                Bitmap image = BitmapFactory.decodeStream(getActivity().openFileInput(user.getImageFileName()));
                profilePicture.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerLayout(View view) {
        //Layout elements: Edit Text
        firstNameView = view.findViewById(R.id.editTextFirstName);
        lastNameView = view.findViewById(R.id.editTextLastName);
        cityView = view.findViewById(R.id.emailTextEdit);
        countryView = view.findViewById(R.id.editTextCountry);
        heightView = view.findViewById(R.id.editTextHeight);
        weightView = view.findViewById(R.id.editTextWeight);

        //Layout elements: Buttons
        Button submitButton = view.findViewById(R.id.button);
        Button cameraButton = view.findViewById(R.id.addPictureButton);
        submitButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        //Radio Group and Buttons
        sexButtonGroup = view.findViewById(R.id.sexRadioGroup);
        maleRadioButton = view.findViewById(R.id.maleRadioButton);
        femaleRadioButton = view.findViewById(R.id.femaleRadioButton);

        //ImageView
        profilePicture = view.findViewById(R.id.profilePicView);
    }

    private void gatherInputAndSubmit() {
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        String sex = "";

        //Get the selection from the radio group.
        int selected_sex = sexButtonGroup.getCheckedRadioButtonId();

        //Check for selection; if it's greater than -1 a selection was made.
        if (selected_sex > -1) {
            //Match the selection up with the appropriate button it's paired with.
            sexRadioButton = view.findViewById(selected_sex);

            sex = sexRadioButton.getText().toString();
        }

        String city = cityView.getText().toString();
        String country = countryView.getText().toString();
        String weight = weightView.getText().toString();
        String height = heightView.getText().toString();

        User updatedUser = new User(firstName, lastName, sex, city, country, weight, height);
        updatedUser.setRegistered(true);

        usersViewModel.select(updatedUser);

        listener.submitButtonClick(firstName, lastName, sex, city, country, weight, height);
    }

    /**
     * Checks that the input of height and weight are numbers for further use between the
     * other views in case the user wants to use this information for BMR and
     * BMI calculations.
     * @param height height to be parsed
     * @param weight weight to be parsed
     * @return true if the inputs are empty, true if either of the inputs is a number,
     *         false if they can't be parsed into numbers.
     */
    private boolean checkHeightAndWeightForDigits(String height, String weight){


        if(height.equals("") && weight.equals("")){
            return true;
        }

        if(!height.equals("")){
            try {
                Integer heightInt = Integer.parseInt(height);
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(), "Height needs to be a number", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(!weight.equals("")){
            try{
                Integer weightInt = Integer.parseInt(weight);
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(), "Weight needs to be number", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

}

