package com.example.b07_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    private DatabaseReference eventReference;
    private ListView listViewEvent;
    private List<String> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        // Initialize Firebase Database reference
        eventReference = FirebaseDatabase.getInstance().getReference("events");

        // Initialize views
        listViewEvent = findViewById(R.id.listViewEvent);
        eventList = new ArrayList<>();

        displayFeedback();

        Button btnBack = findViewById(R.id.btnBack);
        Button btnAddFeedback = findViewById(R.id.btnAddFeedback);
        btnBack.setBackgroundColor(Color.BLUE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the main page
                finish();
            }
        });
        btnAddFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventActivity.this, FeedbackFragment.class);
                startActivity(intent);
            }
        });
    }

    private void displayFeedback() {
        eventReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the previous feedback
                eventList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    long limit = snapshot.child("limit").getValue(Long.class);
                    long day = snapshot.child("day").getValue(Long.class);
                    long month = snapshot.child("month").getValue(Long.class);
                    long year = snapshot.child("year").getValue(Long.class);
                    String feedbackEntry = name + " on " + month + "/" + day + "/" + year;
                    eventList.add(feedbackEntry);
                }

                // Update the ListView and the bottom panel
                updateFeedbackListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }

    // Update the ListView with the current feedbackList
    private void updateFeedbackListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        listViewEvent.setAdapter(adapter);
    }
}