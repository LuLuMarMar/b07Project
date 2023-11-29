package com.example.b07_project;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.view.View;

public class ComplaintActivity extends AppCompatActivity {

    private EditText complaintEditText;
    private DatabaseReference complaintsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        // Initialize UI components
        complaintEditText = findViewById(R.id.complaintEditText);

        // Get a reference to the "complaints" node in the Firebase Realtime Database
        complaintsRef = FirebaseDatabase.getInstance().getReference().child("complaints");
    }

    // Method to submit a complaint
    public void submitComplaint(View view) {
        // Retrieve the text from the complaint input field
        String complaintText = complaintEditText.getText().toString().trim();

        // Check if the complaint text is not empty
        if (!TextUtils.isEmpty(complaintText)) {
            // Generate a unique key for the complaint using push
            String complaintId = complaintsRef.push().getKey();

            // Set the complaint text under the generated key in the "complaints" node
            complaintsRef.child(complaintId).setValue(complaintText);

            // Optionally, associate the complaint with the user who submitted it (assuming Firebase Authentication is used)
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            complaintsRef.child(complaintId).child("userId").setValue(userId);

            // Display a success message
            Toast.makeText(this, "Complaint submitted successfully", Toast.LENGTH_SHORT).show();

            // Close the Complaints activity
            finish();
        } else {
            // Display an error message if the complaint text is empty
            Toast.makeText(this, "Please enter a complaint", Toast.LENGTH_SHORT).show();
        }
    }
}
