package com.example.lifestyleapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.lifecycle.ViewModelProvider;

import java.io.FileNotFoundException;

public class DisplayUserFragment extends Fragment {
    private TextView firstNameDisplay, lastNameDisplay, heightDisplay,
            ageDisplay, weightDisplay, cityDisplay, countryDisplay, sexDisplay;
    private ImageView userImage;
    private ButtonListener listener;
    private UsersViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_display_user_info, container, false);

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
        this.ageDisplay = view.findViewById(R.id.tvDisplayAge);
        this.cityDisplay = view.findViewById(R.id.tvDisplayCity);
        this.countryDisplay = view.findViewById(R.id.tvDisplayCountry);
        this.sexDisplay = view.findViewById(R.id.tvDisplaySex);
        this.heightDisplay = view.findViewById(R.id.tvDisplayHeight);
        this.weightDisplay = view.findViewById(R.id.tvDisplayWeight);
        this.userImage = view.findViewById(R.id.profilePicture);

        userViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);
        userViewModel.getSelected().observe(getViewLifecycleOwner(), user->{
            fillUserInfo(user);
        });

        //When the Edit Profile button is clicked, signal DrawerActivity to activate the Register User Fragment.
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.editProfileClick();
            }
        });

        return view;
    }

    private void fillUserInfo(User user) {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String age = user.getAge();
        String city = user.getCity();
        String country = user.getCountry();
        String sex = user.getSex();
        String height = user.getHeight();
        String weight = user.getWeight();

        firstNameDisplay.setText(firstName);
        lastNameDisplay.setText(lastName);
        ageDisplay.setText(age);
        cityDisplay.setText(city);
        countryDisplay.setText(country);
        sexDisplay.setText(sex);
        heightDisplay.setText(height);
        weightDisplay.setText(weight);

        //It's possible that the image has not been set, so check to see if it's in the User class.
        if(!user.getImageFileName().matches("")){
            //The try/catch block is mostly for openFileInput; it's possible the filepath doesn't exist
            //even if it is in the User class.
            try {
                Bitmap image = BitmapFactory.decodeStream(getActivity().openFileInput(user.getImageFileName()));
                userImage.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
