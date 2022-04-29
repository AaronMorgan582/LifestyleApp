package com.example.lifestyleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.remote.EspressoRemoteMessage;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.GestureDetector;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifestyleapp.Version1Activities.MainActivity;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Handles the layout of the application.
 */
public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ButtonListener, SensorEventListener{

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextView user_name;
    private DrawerLayout drawerLayout;
    private String userName;
    private String userImageFile;
    private ImageView profilePicture;
    private MenuItem stepsDisplay;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean running = false;

    private GestureDetectorCompat gestureDetectorCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        //Grab the menu so the title text can be set
        Menu menu = navigationView.getMenu();
        stepsDisplay = menu.findItem(R.id.step_counter);

        user_name = headerView.findViewById(R.id.navHeaderUserName);
        profilePicture = headerView.findViewById(R.id.navHeaderProfPic);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Gesture

        gestureDetectorCompat = new GestureDetectorCompat(this, new DetectGesture());

        drawerLayout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetectorCompat.onTouchEvent(motionEvent);
            }
        });

        Intent intent = getIntent();
        String str = intent.getExtras().getString("EMAIL");

        // Handle the view model activity
        UsersViewModel model = new ViewModelProvider(this).get(UsersViewModel.class);
        model.initActiveUser(str);

        model.getSelected().observe(this, user ->{


            if(savedInstanceState == null){
                if(user.isRegistered()){
                    user_name.setText(user.getFirstName());
                    userName = (String) user_name.getText();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_home);
                }
                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegisterUserFragment()).commit();
                    navigationView.setCheckedItem(R.id.nav_profile);
                }
            }

        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_profile:
                DisplayUserFragment displayUserFragment = new DisplayUserFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, displayUserFragment).addToBackStack("Profile").commit();
                break;
            case R.id.nav_bmi:
                bmiFragment bmi = new bmiFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bmi).addToBackStack("BMI").commit();
                break;
            case R.id.nav_calories:
                bmrFragment fitness_goals = new bmrFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fitness_goals).addToBackStack("Goals").commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void editProfileClick() {
        Fragment register_fragment = new RegisterUserFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, register_fragment).addToBackStack("Register User");
        ft.commit();
    }

    @Override
    public void submitButtonClick() {
        Fragment displayUserFragment = new DisplayUserFragment();
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
        outState.putString("first_name", userName);
        outState.putString("image_filepath", userImageFile);
        outState.putBoolean("steps_activated", running);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        String first_name = savedInstanceState.getString("first_name");
        String filepath = savedInstanceState.getString("image_filepath");
        Boolean isRunning = savedInstanceState.getBoolean("steps_activated");
        userName = first_name;
        userImageFile = filepath;
        running = isRunning;

        user_name.setText(first_name);
        if(userImageFile != null){
            try {
                Bitmap image = BitmapFactory.decodeStream(openFileInput(userImageFile));
                profilePicture.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    private class DetectGesture extends GestureDetector.SimpleOnGestureListener{
        @Override
        public void onLongPress(MotionEvent e) {
            if(running){
                running = false;
                Toast.makeText(DrawerActivity.this, "Step counter stopped.", Toast.LENGTH_SHORT).show();
            }
            else{
                running = true;
                Toast.makeText(DrawerActivity.this, "Step counter started", Toast.LENGTH_SHORT).show();
            }

            super.onLongPress(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(running){
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if(stepSensor!=null){
                sensorManager.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_NORMAL);
            }else{
                Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(stepSensor!=null){
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(running){
            stepsDisplay.setTitle("Daily Steps: " + String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //Needed for ScrollViews to detect the long press gesture.
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gestureDetectorCompat.onTouchEvent(ev);
    }
}