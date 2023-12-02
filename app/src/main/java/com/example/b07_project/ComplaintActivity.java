package com.example.b07_project;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;

public class ComplaintActivity extends AppCompatActivity {

    private DatabaseReference complaintsReference;
    private EditText complaintEditText;
    private ListView complaintsListView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        complaintsReference = FirebaseDatabase.getInstance().getReference("complaints");

        complaintEditText = findViewById(R.id.complaintEditText);
        complaintsListView = findViewById(R.id.complaintsListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        complaintsListView.setAdapter(adapter);

        Button submitComplaintButton = findViewById(R.id.submitComplaintButton);
        submitComplaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserComplaint();
            }
        });

        Button goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        displayComplaintsFromDatabase();

    }

    private void displayComplaintsFromDatabase() {
        complaintsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> complaintsList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String userComplaint = snapshot.child("comment").getValue(String.class);
                    complaintsList.add(userComplaint);
                }

                adapter.clear();
                adapter.addAll(complaintsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ComplaintActivity.this, "Failed to load complaints.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserComplaint() {
        String userComplaint = complaintEditText.getText().toString().trim();

        if (!userComplaint.isEmpty()) {
            String key = complaintsReference.push().getKey();
            complaintsReference.child(key).child("comment").setValue(userComplaint);

            complaintEditText.setText("");

            Toast.makeText(this, "Complaint submitted successfully", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please enter your complaint", Toast.LENGTH_SHORT).show();
        }
    }
}
