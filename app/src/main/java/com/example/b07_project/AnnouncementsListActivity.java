package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_project.PostAnnouncementsActivity;

import java.util.ArrayList;

public class AnnouncementsListActivity extends AppCompatActivity {

    private ListView announcementsListView;
    private ArrayList<String> announcementsList;
    private ArrayAdapter<String> announcementsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_list);

        announcementsListView = findViewById(R.id.announcementsListView);
        Button backToPostButton = findViewById(R.id.backToMainButton);

        // Initialize the announcements list and adapter
        announcementsList = new ArrayList<>();
        announcementsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, announcementsList);
        announcementsListView.setAdapter(announcementsAdapter);

        // Set click listener for the button
        backToPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPostAnnouncement();
            }
        });

        // Receive posted announcement
        Intent intent = getIntent();
        if (intent != null) {
            String announcement = intent.getStringExtra("announcement");
            ListView announcementsListView = findViewById(R.id.announcementsListView);

            // Create and set the adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            adapter.add(announcement);
            announcementsListView.setAdapter(adapter);
        }
    }

    // Method to update the announcements list
    public void updateAnnouncementsList(String announcement) {
        // Ensure announcementsList is not null
        if (announcementsList != null) {
            announcementsList.add(announcement);
            announcementsAdapter.notifyDataSetChanged();
        }
    }

    public void backToPostAnnouncement() {
        Intent intent = new Intent(this, PostAnnouncementsActivity.class);
        startActivity(intent);
    }
}
