package com.example.lifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button hikeBtn = view.findViewById(R.id.card_hike_btn);
        Button weatherBtn = view.findViewById(R.id.card_weather_btn);
        hikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Rubio's Hike Code goes here", Toast.LENGTH_SHORT).show();
            }
        });
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Rubio's Weather Code goes here", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
