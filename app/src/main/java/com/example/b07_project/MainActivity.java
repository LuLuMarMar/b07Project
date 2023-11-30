package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.b07_project.PostAnnouncementsActivity;
import com.example.b07_project.ReceiveComplaintActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        Button btnAddFeedback = findViewById(R.id.btnViewEvents);
        btnAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventActivity.class);
                startActivity(intent);
            }
        });
      
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

        Button btnComplaints = findViewById(R.id.btnComplaints);
        btnComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open ComplaintActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, ComplaintActivity.class);
                startActivity(intent);
            }
        });

        Button switchComplaintsButton = findViewById(R.id.btnViewComplaints);
        switchComplaintsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReceiveComplaintActivity.class);
                startActivity(intent);
            }
        });

        Button switchAnnouncementsButton = findViewById(R.id.btnPostAnnouncements);
        switchAnnouncementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostAnnouncementsActivity.class);
                startActivity(intent);
            }
        });
    }

}
