package com.example.lifestyleapp.Misc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lifestyleapp.R;
import com.example.lifestyleapp.Version1Activities.RegisterUserActivity;

public class ProfileFragment extends Fragment {

    private Button editProfileBtn;
    private TextView tv_firstName, tv_city, tv_weight;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        tv_city = view.findViewById(R.id.citytv);
        tv_firstName = view.findViewById(R.id.firstNametv);
        tv_weight = view.findViewById(R.id.weighttv);

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("USER_NAME", Context.MODE_PRIVATE);
        String firstName = sharedPreferences.getString("FIRST_NAME", new String());
        String city = sharedPreferences.getString("CITY", new String());
        String weight = sharedPreferences.getString("WEIGHT", new String());

        if(firstName == null || city == null || weight == null ){
            System.out.println("Could not retrieve data");
        }else{
            tv_city.setText(""+city);
            tv_weight.setText(""+weight);
            tv_firstName.setText(""+firstName);
        }

        editProfileBtn = view.findViewById(R.id.editBtn);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        return view;
    }

    private void editProfile(){
        Intent intent = new Intent(getActivity(), RegisterUserActivity.class);
        startActivity(intent);
    }
}
