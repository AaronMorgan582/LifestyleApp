package com.example.lifestyleapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DisplayUserFragment extends Fragment {
    private TextView firstNameDisplay, lastNameDisplay, heightDisplay, weightDisplay, cityDisplay, countryDisplay, sexDisplay;
    private Button editProfile;
    private ImageView userImage;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_display_user_info, container, false);

        //Get the context that this fragment is attached to (which should be the DrawerActivity).
        context = container.getContext();

        //Layout elements
        this.editProfile = view.findViewById(R.id.buttonEditProfile);
        this.firstNameDisplay = view.findViewById(R.id.tvDisplayFirstName);
        this.lastNameDisplay =  view.findViewById(R.id.tvDisplayLastName);
        this.cityDisplay = view.findViewById(R.id.tvDisplayCity);
        this.countryDisplay = view.findViewById(R.id.tvDisplayCountry);
        this.sexDisplay = view.findViewById(R.id.tvDisplaySex);
        this.heightDisplay = view.findViewById(R.id.tvDisplayHeight);
        this.weightDisplay = view.findViewById(R.id.tvDisplayWeight);
        this.userImage = view.findViewById(R.id.profilePicture);

        String user_key = RegisterUserActivity.getUserName();
        SharedPreferences sp = getActivity().getSharedPreferences(user_key, Context.MODE_PRIVATE);

        String firstName = sp.getString("FIRST_NAME", "");
        String lastName = sp.getString("LAST_NAME", "");
        String city = sp.getString("CITY", "");
        String country = sp.getString("COUNTRY", "");
        String sex = sp.getString("SEX", "");
        String height = sp.getString("HEIGHT", "");
        String weight = sp.getString("WEIGHT", "");

        firstNameDisplay.setText(firstName);
        lastNameDisplay.setText(lastName);
        cityDisplay.setText(city);
        countryDisplay.setText(country);
        sexDisplay.setText(sex);
        heightDisplay.setText(height);
        weightDisplay.setText(weight);

        //When the Edit Profile button is clicked, signal DrawerActivity to activate the Register User Fragment.
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }

}
