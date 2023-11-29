package com.example.b07_project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    private DatabaseReference feedbackReference;
    private ListView listViewFeedback;
    private List<String> feedbackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialize Firebase Database reference
        feedbackReference = FirebaseDatabase.getInstance().getReference("feedbacks");

        // Initialize views
        listViewFeedback = findViewById(R.id.listViewFeedback);
        feedbackList = new ArrayList<>();

        // Uncomment the following line when you have real-time data available
        // displayFeedback();

        // Display placeholder feedback initially
        displayFeedback();
    }

    // Display feedback (either hardcoded placeholder or real-time from Firebase)
    private void displayFeedback() {
        // Clear the previous feedback
        feedbackList.clear();

        // For now, use hardcoded placeholder data
        feedbackList.add("Good feedback 1 (Rating: 4)");
        feedbackList.add("Excellent feedback 2 (Rating: 5)");
        feedbackList.add("Average feedback 3 (Rating: 3)");

        // Uncomment the following lines when you have real-time data available
        /*
        feedbackReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String comment = snapshot.child("comment").getValue(String.class);
                    long rating = snapshot.child("rating").getValue(Long.class);
                    String feedbackEntry = comment + " (Rating: " + rating + ")";
                    feedbackList.add(feedbackEntry);
                }

                // Update the ListView and the bottom panel
                updateFeedbackListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
        */

        // Update the ListView and the bottom panel
        updateFeedbackListView();
    }

    // Update the ListView with the current feedbackList
    private void updateFeedbackListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feedbackList);
        listViewFeedback.setAdapter(adapter);

        // Update the count of reviews
        TextView tvReviewCount = findViewById(R.id.tvReviewCount);
        tvReviewCount.setText("Reviews: " + feedbackList.size());

        // Calculate and update the average rating
        float averageRating = calculateAverageRating();
        TextView tvAverageRating = findViewById(R.id.tvAverageRating);
        tvAverageRating.setText(String.format("Avg Rating: %.1f", averageRating));
    }

    // Calculate the average rating from the current feedbackList
    private float calculateAverageRating() {
        if (feedbackList.isEmpty()) {
            return 0; // To avoid division by zero
        }

        int totalReviews = feedbackList.size();
        float totalRating = 0;

        for (String feedbackEntry : feedbackList) {
            int rating = Integer.parseInt(feedbackEntry.substring(feedbackEntry.lastIndexOf(":") + 2, feedbackEntry.lastIndexOf(")")));
            totalRating += rating;
        }

        return totalRating / totalReviews;
    }
}