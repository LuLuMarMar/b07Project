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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeedbackActivity extends AppCompatActivity {

    private DatabaseReference feedbackReference;
    private ListView listViewFeedback;
    private List<String> feedbackList;
    private ArrayAdapter<String> feedbackAdapter;
    private Spinner spinnerEvents;
    private TextView tvReviewCount;
    private TextView tvAverageRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackReference = FirebaseDatabase.getInstance().getReference("feedback");

        initializeViews();

        setupEventSpinner();

        setupBackButton();


    }

    private void initializeViews() {
        listViewFeedback = findViewById(R.id.listViewFeedback);
        feedbackList = new ArrayList<>();
        feedbackAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feedbackList);
        listViewFeedback.setAdapter(feedbackAdapter);

        spinnerEvents = findViewById(R.id.spinnerEvents);
        tvReviewCount = findViewById(R.id.tvReviewCount);
        tvAverageRating = findViewById(R.id.tvAverageRating);
    }

    private void setupEventSpinner() {
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEvents.setAdapter(eventAdapter);

        spinnerEvents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedEvent = parentView.getItemAtPosition(position).toString();
                displayFeedbackForEvent(selectedEvent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        displayFeedback();
    }

    private void setupBackButton() {
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
                Set<String> eventNames = new HashSet<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String comment = snapshot.child("comment").getValue(String.class);
                    long rating = snapshot.child("rating").getValue(Long.class);

                    String feedbackEntry = String.format("Comment: %s \nRating: %d/5", comment, rating);
                    feedbackList.add(feedbackEntry);

                    eventNames.add(snapshot.child("name").getValue(String.class));
                }

                updateSpinnerWithEvents(new ArrayList<>(eventNames));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }

    private void displayFeedbackForEvent(String selectedEvent) {
        feedbackReference.orderByChild("name").equalTo(selectedEvent).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> filteredFeedbackList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String comment = snapshot.child("comment").getValue(String.class);
                    long rating = snapshot.child("rating").getValue(Long.class);

                    String feedbackEntry = String.format("Comment: %s \nRating: %d/5", comment, rating);
                    filteredFeedbackList.add(feedbackEntry);
                }

                feedbackAdapter.clear();
                feedbackAdapter.addAll(filteredFeedbackList);
                feedbackAdapter.notifyDataSetChanged();

                updateBottomPanel(filteredFeedbackList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }

    private void updateSpinnerWithEvents(List<String> eventNames) {
        ArrayAdapter<String> eventAdapter = new ArrayAdapter<>(FeedbackActivity.this, android.R.layout.simple_spinner_item, eventNames);
        eventAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEvents.setAdapter(eventAdapter);
    }

    private void updateBottomPanel(List<String> feedbackForEvent) {
        tvReviewCount.setText("Reviews: " + feedbackForEvent.size());

        float averageRating = calculateAverageRating(feedbackForEvent);
        tvAverageRating.setText(String.format("Avg Rating: %.1f", averageRating));
    }

    private float calculateAverageRating(List<String> feedbackForEvent) {
        if (feedbackForEvent.isEmpty()) {
            return 0;
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