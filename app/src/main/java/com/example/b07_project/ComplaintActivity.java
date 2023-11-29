package com.example.b07_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ComplaintActivity extends AppCompatActivity {

    private DatabaseReference complaintsReference;
    private EditText complaintEditText;
    private TextView tvDisplayComplaints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        // Initialize Firebase Database reference
        complaintsReference = FirebaseDatabase.getInstance().getReference("complaints");

        // Initialize views
        complaintEditText = findViewById(R.id.complaintEditText);
        tvDisplayComplaints = findViewById(R.id.tvDisplayComplaints);

        // Set up the button click event
        Button submitComplaintButton = findViewById(R.id.submitComplaintButton);
        submitComplaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserComplaint();
            }
        });

        // Display complaints from the database
        displayComplaintsFromDatabase();
    }

    // Display complaints from the database
    private void displayComplaintsFromDatabase() {
        complaintsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder complaintsStringBuilder = new StringBuilder();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userComplaint = snapshot.child("comment").getValue(String.class);
                    complaintsStringBuilder.append(userComplaint).append("\n");
                }

                // Update the TextView with complaints
                tvDisplayComplaints.setText(complaintsStringBuilder.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
                Toast.makeText(ComplaintActivity.this, "Failed to load complaints.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Add user complaint to the database
    private void addUserComplaint() {
        String userComplaint = complaintEditText.getText().toString().trim();

        if (!userComplaint.isEmpty()) {
            // Push the user complaint to the database
            String key = complaintsReference.push().getKey();
            complaintsReference.child(key).child("comment").setValue(userComplaint);

            // Clear the input field
            complaintEditText.setText("");


            // Inform the user that the complaint has been submitted
            Toast.makeText(this, "Complaint submitted successfully", Toast.LENGTH_LONG).show();
        } else {
            // Inform the user that the complaint cannot be empty
            Toast.makeText(this, "Please enter your complaint", Toast.LENGTH_SHORT).show();
        }
    }
}
