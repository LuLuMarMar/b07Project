package com.example.b07_project;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// ComplaintActivity.java
public class ComplaintActivity extends AppCompatActivity {

    private DatabaseReference complaintsRef;
    private EditText complaintEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        // Initialize Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        complaintsRef = database.getReference("complaints");

        complaintEditText = findViewById(R.id.complaintEditText);

        // Button click to submit complaint
        Button submitComplaintButton = findViewById(R.id.submitComplaintButton);
        submitComplaintButton.setOnClickListener(view -> submitComplaint());
    }

    private void submitComplaint() {
        // Get the current user ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            String complaintText = complaintEditText.getText().toString();

            Complaint complaint = new Complaint(userId, complaintText);

            // Push the complaint to the database
            String key = complaintsRef.push().getKey();
            complaintsRef.child(key).setValue(complaint);

            // Optionally, you can add success/failure handling here
        } else {
            // Handle the case where the user is not authenticated
            // You might want to redirect them to the login screen or take appropriate action
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }
}
