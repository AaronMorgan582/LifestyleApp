package com.example.lifestyleapp;

import android.content.Intent;
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
    private TextView firstNameDisplay, lastNameDisplay, height, weight, city, country, sex;
    private Button editProfile;
    private ImageView userImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_display_user_info, container, false);

        //Layout elements
        this.editProfile = view.findViewById(R.id.buttonEditProfile);
        this.firstNameDisplay = view.findViewById(R.id.tvDisplayFirstName);
        this.lastNameDisplay =  view.findViewById(R.id.tvDisplayLastName);
        this.city = view.findViewById(R.id.tvDisplayCity);
        this.country = view.findViewById(R.id.tvDisplayCountry);
        this.sex = view.findViewById(R.id.tvDisplaySex);
        this.height = view.findViewById(R.id.tvDisplayHeight);
        this.weight = view.findViewById(R.id.tvDisplayWeight);
        this.userImage = view.findViewById(R.id.profilePicture);



        return view;
    }
}
