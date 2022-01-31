package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterUserActivity extends AppCompatActivity {
    private Button submitButton;
    private EditText firstNameView;
    private EditText lastNameView;
    private String firstName;
    private String lastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        submitButton = (Button) findViewById(R.id.button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUserInfo();
            }
        });

    }
    /**
     * Temporary method to show a summary of the user information
     * in another activity
     * */
    public void showUserInfo(){
        firstNameView = (EditText) findViewById(R.id.editTextFirstName);
        lastNameView = (EditText) findViewById(R.id.editTextLastName);
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        // Need error checking in case string is null or empty

        Intent displayUserInfoIntent = new Intent(this, DisplayUserInfoActivity.class);
        displayUserInfoIntent.putExtra("USER_FIRST_NAME", firstName);
        displayUserInfoIntent.putExtra("USER_LAST_NAME", lastName);
        startActivity(displayUserInfoIntent);

    }
}