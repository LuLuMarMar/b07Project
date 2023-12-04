package com.example.b07_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_admin_main);

        FirebaseUser user;
        String email;

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        email = user.getEmail();
        assert email != null;
        boolean isAdmin = email.contains("@admin_mail");

        //Connecting buttons from layout and changing colors
        Button btnViewFeedback = findViewById(R.id.btnViewFeedback);
        btnViewFeedback.setBackgroundColor(Color.parseColor("#007FA3"));

        Button switchComplaintsButton = findViewById(R.id.btnViewComplaints);
        switchComplaintsButton.setBackgroundColor(Color.parseColor("#007FA3"));

        Button switchAnnouncementsButton = findViewById(R.id.btnPostAnnouncements);
        switchAnnouncementsButton.setBackgroundColor(Color.parseColor("#007FA3"));

        Button btnGoToForm = findViewById(R.id.btnGoToForm);
        btnGoToForm.setBackgroundColor(Color.parseColor("#007FA3"));

        //View Feedback Button (For Events)
        btnViewFeedback.setOnClickListener(v -> {
            // Open FeedbackActivity when the button is clicked
            Intent intent = new Intent(AdminHomeActivity.this, FeedbackActivity.class);
            startActivity(intent);
        });

        //View Complaints Button
        switchComplaintsButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, ReceiveComplaintActivity.class);
            startActivity(intent);
        });

        //Post Announcements Button
        switchAnnouncementsButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomeActivity.this, PostAnnouncementsActivity.class);
            startActivity(intent);
        });

        //Schedule Events Button
        btnGoToForm.setOnClickListener(v -> {
            // Open FormActivity when the button is clicked
            Intent intent = new Intent(AdminHomeActivity.this, FormActivity.class);
            startActivity(intent);
        });

        //Sign Out Button
        Button btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setBackgroundColor(Color.parseColor("#007FA3"));
        btnSignOut.setOnClickListener(v -> {
//        Sign out the user and go back to the login page
        FirebaseAuth.getInstance().signOut();
        finish();
        });
    }
}

