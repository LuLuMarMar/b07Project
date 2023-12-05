package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostAnnouncementsActivity extends AppCompatActivity {

    private EditText announcementEditText;
    private TextView announcementTextView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_post);

        announcementEditText = findViewById(R.id.announcementEditText);
        announcementTextView = findViewById(R.id.announcementTextView);
        Button postButton = findViewById(R.id.postButton);
        Button postbackToMainButton = findViewById(R.id.PostbackToMainButton);

        postButton.setOnClickListener(view -> postAnnouncement());
        postbackToMainButton.setOnClickListener(v -> backToMain());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("announcement");
    }

    private void postAnnouncement() {
        String newAnnouncement = announcementEditText.getText().toString();

        // Display the new announcement
        String currentAnnouncements = announcementTextView.getText().toString();
        String updatedAnnouncements = currentAnnouncements + "\n\n" + newAnnouncement;
        announcementTextView.setText(updatedAnnouncements);

        // Pass the new announcement to AnnouncementsListActivity
        Intent intent = new Intent(this, AnnouncementsListActivity.class);
        intent.putExtra("announcement", newAnnouncement);
        startActivity(intent);

        // Upload the new announcement to Firebase
        String key = databaseReference.push().getKey();
        if (key != null) {
            databaseReference.child(key).setValue(newAnnouncement);
        } else {
            // Handle the case where key is null
            Log.e("AnnouncementActivity", "Failed to generate a key for the announcement");
        }

        // Show a notification for the new announcement
        NotificationHelper.showAnnouncementNotification(this, "New Announcement", newAnnouncement);

        // Clear the EditText
        announcementEditText.setText("");
    }

    public void switchToAnnouncementsList(View view) {
        Intent intent = new Intent(this, AnnouncementsListActivity.class);
        startActivity(intent);
    }
    public void backToMain() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
