package com.example.b07_project;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    private DatabaseReference feedbackReference;
    private ListView listViewFeedback;
    private List<String> feedbackList;
    private ArrayAdapter<String> feedbackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackReference = FirebaseDatabase.getInstance().getReference("feedback");

        listViewFeedback = findViewById(R.id.listViewFeedback);
        feedbackList = new ArrayList<>();
        feedbackAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feedbackList);
        listViewFeedback.setAdapter(feedbackAdapter);

        // Initialize and set up the Spinner for event selection
        Spinner spinnerEvents = findViewById(R.id.spinnerEvents);
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEvents.setAdapter(eventAdapter);

        // Set up item selection listener for the Spinner
        spinnerEvents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Update feedback based on the selected event
                displayFeedbackForEvent(spinnerEvents.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        displayFeedback();

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setBackgroundColor(Color.BLUE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayFeedback() {
        feedbackReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feedbackList.clear();
                List<String> eventNames = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String comment = snapshot.child("comment").getValue(String.class);
                    long rating = snapshot.child("rating").getValue(Long.class);
                    String eventName = snapshot.child("name").getValue(String.class);

                    String feedbackEntry = "Event: " + eventName + "\nComment: " + comment + " \nRating: " + rating + "/5";
                    feedbackList.add(feedbackEntry);

                    // Populate the list of event names for the Spinner
                    if (!eventNames.contains(eventName)) {
                        eventNames.add(eventName);
                    }
                }

                // Update the Spinner with event names
                Spinner spinnerEvents = findViewById(R.id.spinnerEvents);
                ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(FeedbackActivity.this, android.R.layout.simple_spinner_item, eventNames);
                eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerEvents.setAdapter(eventAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }

    private void displayFeedbackForEvent(String selectedEvent) {
        // Filter feedbackList for the selected event
        List<String> filteredFeedbackList = new ArrayList<>();
        for (String feedback : feedbackList) {
            if (feedback.contains("Event: " + selectedEvent)) {
                filteredFeedbackList.add(feedback);
            }
        }

        // Update the ListView and the bottom panel for the selected event
        feedbackAdapter.clear();
        feedbackAdapter.addAll(filteredFeedbackList);
        feedbackAdapter.notifyDataSetChanged();

        updateFeedbackListView(filteredFeedbackList);
        updateBottomPanel(filteredFeedbackList);

    }

    private void updateFeedbackListView(List<String> updatedFeedbackList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, updatedFeedbackList);
        listViewFeedback.setAdapter(adapter);
    }

    private void updateBottomPanel(List<String> feedbackForEvent) {
        // Update the count of reviews
        TextView tvReviewCount = findViewById(R.id.tvReviewCount);
        tvReviewCount.setText("Reviews: " + feedbackForEvent.size());

        // Calculate and update the average rating
        float averageRating = calculateAverageRating(feedbackForEvent);
        TextView tvAverageRating = findViewById(R.id.tvAverageRating);
        tvAverageRating.setText(String.format("Avg Rating: %.1f", averageRating));
    }

    private float calculateAverageRating(List<String> feedbackForEvent) {
        if (feedbackForEvent.isEmpty()) {
            return 0; // To avoid division by zero
        }

        int totalReviews = feedbackForEvent.size();
        float totalRating = 0;

        for (String feedbackEntry : feedbackForEvent) {
            int rating = Integer.parseInt(feedbackEntry.substring(feedbackEntry.lastIndexOf(":") + 2, feedbackEntry.lastIndexOf("/")));
            totalRating += rating;
        }

        return totalRating / totalReviews;
    }
}