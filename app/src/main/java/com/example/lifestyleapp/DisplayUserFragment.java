package com.example.lifestyleapp;

import android.content.Context;
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
    private ImageView userImage;
    private ButtonListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_display_user_info, container, false);

        //Get the context that this fragment is attached to (which should be the DrawerActivity).
        Context context = container.getContext();
        try{
            listener = (ButtonListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement ButtonListener interface.");
        }

        //Layout elements
        Button editProfile = view.findViewById(R.id.buttonEditProfile);
        this.firstNameDisplay = view.findViewById(R.id.tvDisplayFirstName);
        this.lastNameDisplay =  view.findViewById(R.id.tvDisplayLastName);
        this.cityDisplay = view.findViewById(R.id.tvDisplayCity);
        this.countryDisplay = view.findViewById(R.id.tvDisplayCountry);
        this.sexDisplay = view.findViewById(R.id.tvDisplaySex);
        this.heightDisplay = view.findViewById(R.id.tvDisplayHeight);
        this.weightDisplay = view.findViewById(R.id.tvDisplayWeight);
        this.userImage = view.findViewById(R.id.profilePicture);

        if (getArguments() != null) {
            fillUserInfo();
        }

        //When the Edit Profile button is clicked, signal DrawerActivity to activate the Register User Fragment.
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.editProfileClick();
            }
        });

        return view;
    }

    private void fillUserInfo() {
        User user = getArguments().getParcelable("user_data");
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String city = user.getCity();
        String country = user.getCountry();
        String sex = user.getGender();
        String height = user.getHeight();
        String weight = user.getWeight();

        firstNameDisplay.setText(firstName);
        lastNameDisplay.setText(lastName);
        cityDisplay.setText(city);
        countryDisplay.setText(country);
        sexDisplay.setText(sex);
        heightDisplay.setText(height);
        weightDisplay.setText(weight);
    }
}