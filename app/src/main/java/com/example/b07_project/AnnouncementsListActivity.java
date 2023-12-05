package com.example.b07_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseListAdapter;

public class AnnouncementsListActivity extends AppCompatActivity {

    private FirebaseListAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_list);

        Button backToPostButton = findViewById(R.id.backToPostButton);
        DatabaseReference announcementsReference = FirebaseDatabase.getInstance().getReference().child("announcement");

        ListView announcementsListView = findViewById(R.id.announcementsListView);

        FirebaseListOptions<String> options = new FirebaseListOptions.Builder<String>()
                .setLayout(android.R.layout.simple_list_item_1)
                .setQuery(announcementsReference, String.class)
                .build();

        adapter = new FirebaseListAdapter<String>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull String model, int position) {
                TextView textView = v.findViewById(android.R.id.text1);
                textView.setText(model);
            }
        };

        announcementsListView.setAdapter(adapter);

        //backToPostButton.setOnClickListener(v -> backToPostAnnouncement());

        // Back button to return to MainActivity
        Button btnBack = findViewById(R.id.backToPostButton);
        btnBack.setBackgroundColor(Color.BLUE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the main page
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void backToPostAnnouncement() {
        Intent intent = new Intent(this, PostAnnouncementsActivity.class);
        startActivity(intent);
    }
}
