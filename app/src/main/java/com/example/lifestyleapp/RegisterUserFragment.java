package com.example.lifestyleapp;

import static android.app.Activity.RESULT_OK;

import android.content.ActivityNotFoundException;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegisterUserFragment extends Fragment implements View.OnClickListener, ButtonListener{
    private RadioGroup sexButtonGroup;
    private RadioButton sexRadioButton, maleRadioButton, femaleRadioButton;
    private EditText firstNameView, lastNameView, cityView, countryView, heightView, weightView;
    private ButtonListener listener;
    private View view;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_register_user, container, false);

        //Ensure that the activity coupled with this fragment has the ButtonListener interface implemented.
        Context context = container.getContext();
        try{
            listener = (ButtonListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement ButtonListener interface.");
        }

        //Get the elements from the layout.
        registerLayout(view);

        //If there are arguments from the Drawer Activity, use it to fill out the input fields.
        if (getArguments() != null) {
            User user_data = getArguments().getParcelable("user_data");
            autoFill(user_data);
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
                cameraButtonClick();
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
        if(user.getGender().matches("Female")){
            femaleRadioButton.setChecked(true);
        }
        else if(user.getGender().matches("Male")){
            maleRadioButton.setChecked(true);
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
        Button submitButton = view.findViewById(R.id.button);
        Button cameraButton = view.findViewById(R.id.addPictureButton);
        submitButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);

        //Radio Group and Buttons
        sexButtonGroup = view.findViewById(R.id.sexRadioGroup);
        maleRadioButton = view.findViewById(R.id.maleRadioButton);
        femaleRadioButton = view.findViewById(R.id.femaleRadioButton);
    }

    private void gatherInputAndSubmit() {
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();

        //Get the selection from the radio group.
        int selected_sex = sexButtonGroup.getCheckedRadioButtonId();

        //Match the selection up with the appropriate button it's paired with.
        sexRadioButton = view.findViewById(selected_sex);

        String sex = sexRadioButton.getText().toString();
        String city = cityView.getText().toString();
        String country = countryView.getText().toString();
        String weight = weightView.getText().toString();
        String height = heightView.getText().toString();
        listener.submitButtonClick(firstName, lastName, sex, city, country, weight, height);
    }

    @Override
    public void editProfileClick() {

        Toast.makeText(getActivity(), "Edit profile Click", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void submitButtonClick(String firstName, String lastName, String gender, String city, String country, String weight, String height) {

        Toast.makeText(getActivity(), "Submit click", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void cameraButtonClick() {

        Intent takeProfilePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try{
            startActivityForResult(takeProfilePic, REQUEST_IMAGE_CAPTURE);
        }catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Could not open Camera Intent", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getActivity(), "Camera Click", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
           // imageView.setImageBitmap(imageBitmap);
        }
    }


}
