package com.example.lifestyleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Handles the layout of the application.
 */
public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ButtonListener{

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextView user_name;
    private DrawerLayout drawerLayout;
    private User user;
    private String userImageFile;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        user_name = headerView.findViewById(R.id.navHeaderUserName);
        profilePicture = headerView.findViewById(R.id.navHeaderProfPic);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Intent intent = getIntent();
        String str = intent.getExtras().getString("EMAIL");

        // Handle the view model activity
        UsersViewModel model = new ViewModelProvider(this).get(UsersViewModel.class);
        model.initActiveUser(str);

        model.getSelected().observe(this, user ->{
            // something happens here. Retrieve the found user from the db
            // what happens if doesn't exist?

            if(user == null){

            }
        });

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegisterUserFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_profile);
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, displayUserFragment).addToBackStack("Profile").commit();
                break;
            case R.id.nav_bmi:
                bmiFragment bmi = new bmiFragment();
                if(user != null){
                    Bundle fragment_bundle = new Bundle();
                    fragment_bundle.putParcelable("user_data", user);
                    bmi.setArguments(fragment_bundle);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bmi).addToBackStack("BMI").commit();
                break;
            case R.id.nav_calories:
                bmrFragment fitness_goals = new bmrFragment();
                if(user != null){
                    Bundle fragment_bundle = new Bundle();
                    fragment_bundle.putParcelable("user_data", user);
                    fitness_goals.setArguments(fragment_bundle);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fitness_goals).addToBackStack("Goals").commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void editProfileClick() {
        Fragment register_fragment = new RegisterUserFragment();

        /*
        if(user != null){
            Bundle fragment_bundle = new Bundle();
            fragment_bundle.putParcelable("user_data", user);
            register_fragment.setArguments(fragment_bundle);
        }

         */

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, register_fragment).addToBackStack("Register User");
        ft.commit();
    }

    @Override
    public void submitButtonClick(String firstName, String lastName, String gender, String city, String country, String weight, String height) {
        /*
        user = new User(firstName, lastName, gender, city, country, weight, height);
        user_name.setText(firstName + " " + lastName);
        if(userImageFile != null){
            user.setImageFileName(userImageFile);
        }
        Bundle fragment_bundle = new Bundle();
        fragment_bundle.putParcelable("user_data", user);
        */

        Fragment displayUserFragment = new DisplayUserFragment();

        //displayUserFragment.setArguments(fragment_bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, displayUserFragment).addToBackStack("Profile");
        ft.commit();
    }

    @Override
    public void cameraButtonClick() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Could not take picture", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap userImage = (Bitmap) extras.get("data");
            profilePicture.setImageBitmap(userImage);
            userImageFile = saveImageToString(userImage);
            Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Image could not be saved", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * This method uses a byte array stream to write the image to the app's internal storage.
     * Does not save the image externally.
     * @param image image to be saved
     * @return name of file
     */
    private String saveImageToString(Bitmap image){
        String fileName = "user_Image";
        try{
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
        }catch (Exception e){
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if(user != null){
            outState.putString("first_name", user.getFirstName());
            outState.putString("last_name", user.getLastName());
            outState.putString("gender", user.getGender());
            outState.putString("city", user.getCity());
            outState.putString("country", user.getCountry());
            outState.putString("weight", user.getWeight());
            outState.putString("height", user.getHeight());
            outState.putString("image_filepath", user.getImageFileName());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        String first_name = savedInstanceState.getString("first_name");
        String last_name = savedInstanceState.getString("last_name");
        String gender = savedInstanceState.getString("gender");
        String city = savedInstanceState.getString("city");
        String country = savedInstanceState.getString("country");
        String weight = savedInstanceState.getString("weight");
        String height = savedInstanceState.getString("height");
        String filepath = savedInstanceState.getString("image_filepath");

        user = new User(first_name, last_name, gender, city, country, weight, height);
        user.setImageFileName(filepath);
        user_name.setText(first_name + " " + last_name);
        try {
            Bitmap image = BitmapFactory.decodeStream(openFileInput(filepath));
            profilePicture.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    private void selectViewToDisplay(User user){
        if(user == null){
            System.out.println("Null");
        }
        if(user.equals(null)){
            System.out.println("Null");
        }
        else{
            System.out.println("Not null");
        }
    }
}