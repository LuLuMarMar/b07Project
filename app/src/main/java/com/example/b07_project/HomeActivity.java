package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        FirebaseUser user;
        String email;

        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail().toString();
        boolean isAdmin = email.contains("@adminmail");

        if (!isAdmin) {
            //View Events Button
            Button btnAddFeedback = findViewById(R.id.btnViewEvents);
            btnAddFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, EventActivity.class);
                    startActivity(intent);
                }
            });

            //Complaints button
            Button btnComplaints = findViewById(R.id.btnComplaints);
            btnComplaints.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open ComplaintActivity when the button is clicked
                    Intent intent = new Intent(HomeActivity.this, ComplaintActivity.class);
                    startActivity(intent);
                }
            });

            //Add Post Button
            Button btnPostReq = findViewById(R.id.btnPostReq);
            btnPostReq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open FeedbackActivity when the button is clicked
                    Intent intent = new Intent(HomeActivity.this, PostreqActivity.class);
                    startActivity(intent);
                }
            });

            //Add Receive Notifications/Announcements
            Button btnViewAnnouncements = findViewById(R.id.btnNotifications);
            btnViewAnnouncements.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open FeedbackActivity when the button is clicked
                    Intent intent = new Intent(HomeActivity.this, NotificationsActivity.class);
                    startActivity(intent);
                }
            });
        }

        else {
            //View Feedback Button (For Events)
            Button btnViewFeedback = findViewById(R.id.btnViewFeedback);
            btnViewFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open FeedbackActivity when the button is clicked
                    Intent intent = new Intent(HomeActivity.this, FeedbackActivity.class);
                    startActivity(intent);
                }
            });

            //View Complaints Button
            Button switchComplaintsButton = findViewById(R.id.btnViewComplaints);
            switchComplaintsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, ReceiveComplaintActivity.class);
                    startActivity(intent);
                }
            });

            //Post Announcements Button
            Button switchAnnouncementsButton = findViewById(R.id.btnPostAnnouncements);
            switchAnnouncementsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, PostAnnouncementsActivity.class);
                    startActivity(intent);
                }
            });

            //Schedule Events Button
            Button btnGoToForm = findViewById(R.id.btnGoToForm);
            btnGoToForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open FormActivity when the button is clicked
                    Intent intent = new Intent(HomeActivity.this, FormActivity.class);
                    startActivity(intent);
                }
            });
        }

        //Sign Out Button
        Button btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open FeedbackActivity when the button is clicked
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}

