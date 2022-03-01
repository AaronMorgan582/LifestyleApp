package com.example.lifestyleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ButtonListener{

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private DrawerLayout drawerLayout;
    private User user;
    private Bitmap user_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_profile:
                DisplayUserFragment displayUserFragment = new DisplayUserFragment();
                if(user != null){
                    Bundle fragment_bundle = new Bundle();
                    fragment_bundle.putParcelable("user_data", user);
                    displayUserFragment.setArguments(fragment_bundle);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, displayUserFragment).commit();
                break;
            case R.id.nav_bmi:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new bmiFragment()).commit();
                break;
            case R.id.nav_calories:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new bmrFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void editProfileClick() {
        Fragment register_fragment = new RegisterUserFragment();
        if(user != null){
            Bundle fragment_bundle = new Bundle();
            fragment_bundle.putParcelable("user_data", user);
            register_fragment.setArguments(fragment_bundle);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, register_fragment);
        ft.commit();
    }

    @Override
    public void submitButtonClick(String firstName, String lastName, String gender, String city, String country, String weight, String height) {
        user = new User(firstName, lastName, gender, city, country, weight, height);
        Bundle fragment_bundle = new Bundle();
        fragment_bundle.putParcelable("user_data", user);
        Fragment displayUserFragment = new DisplayUserFragment();

        displayUserFragment.setArguments(fragment_bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, displayUserFragment);
        ft.commit();
    }

    @Override
    public void cameraButtonClick() {
    }

}