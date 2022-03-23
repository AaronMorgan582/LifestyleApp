package com.example.lifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class LoggingPlaceHolderActivity extends AppCompatActivity {

    private Button logIn;
    private EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging_place_holder);
        logIn = findViewById(R.id.login_btn);
        input = findViewById(R.id.emailTextEdit);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistrationView();
            }
        });
    }
    public void openRegistrationView(){

        String str = input.getText().toString();
        Intent intent = new Intent(this, DrawerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("EMAIL", str);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}