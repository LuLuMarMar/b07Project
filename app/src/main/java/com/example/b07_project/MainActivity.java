package com.example.b07_project;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_project.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGoToForm = findViewById(R.id.btnGoToForm);
        btnGoToForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open FormActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
            }
        });

        Button btnViewFeedback = findViewById(R.id.btnViewFeedback);
        btnViewFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open FeedbackActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        });

        Button btnSubmitComplaints = findViewById(R.id.btnComplaints);
        btnSubmitComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open FeedbackActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, ComplaintActivity.class);
                startActivity(intent);
            }
        });



    }

}

