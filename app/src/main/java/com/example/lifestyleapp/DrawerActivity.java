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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ButtonListener{

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private DrawerLayout drawerLayout;
    private User user;
    private Bitmap user_image;
    private ImageView profPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        profPic = headerView.findViewById(R.id.navHeaderProfPic);
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
            case R.id.nav_weather:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WeatherFragment()).commit();
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
        saveImageBitmap();
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
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profPic.setImageBitmap(imageBitmap);
        }
    }
    private void saveImageBitmap (){

        BitmapDrawable drawable = (BitmapDrawable) profPic.getDrawable();
        Bitmap image = drawable.getBitmap();

        FileOutputStream outputStream;
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/PictureFolder");
        directory.mkdir();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG" + timeStamp + "_";
        File outFile = new File(directory, imageFileName);
        try{
            outputStream = new FileOutputStream(outFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        }catch(FileNotFoundException e){
            Toast.makeText(this, "File Not found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}