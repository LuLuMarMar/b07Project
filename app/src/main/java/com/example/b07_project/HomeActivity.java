package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseUser user;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail().toString();
        boolean isAdmin = email.contains("@admin_mail");

        if (!isAdmin) {
            Button btnAddFeedback = findViewById(R.id.btnViewEvents);
            btnAddFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, EventActivity.class);
                    startActivity(intent);
                }
            });

            Button btnComplaints = findViewById(R.id.btnComplaints);
            btnComplaints.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open ComplaintActivity when the button is clicked
                    Intent intent = new Intent(HomeActivity.this, ComplaintActivity.class);
                    startActivity(intent);
                }
            });

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

        if (isAdmin) {
            Button btnViewFeedback = findViewById(R.id.btnViewFeedback);
            btnViewFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open FeedbackActivity when the button is clicked
                    Intent intent = new Intent(HomeActivity.this, FeedbackActivity.class);
                    startActivity(intent);
                }
            });

            Button switchComplaintsButton = findViewById(R.id.btnViewComplaints);
            switchComplaintsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, ReceiveComplaintActivity.class);
                    startActivity(intent);
                }
            });

            Button switchAnnouncementsButton = findViewById(R.id.btnPostAnnouncements);
            switchAnnouncementsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, PostAnnouncementsActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

}
