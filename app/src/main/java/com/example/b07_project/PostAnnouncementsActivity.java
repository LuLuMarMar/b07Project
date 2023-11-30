package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostAnnouncementsActivity extends AppCompatActivity {

    private EditText announcementEditText;
    private Button postButton;
    private TextView announcementTextView;
    private DatabaseReference databaseReference;
    private Button postbackToMainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements_post);

        announcementEditText = findViewById(R.id.announcementEditText);
        postButton = findViewById(R.id.postButton);
        announcementTextView = findViewById(R.id.announcementTextView);
        postbackToMainButton = findViewById(R.id.PostbackToMainButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAnnouncement();
            }
        });

        postbackToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

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
        String key = databaseReference.push().getKey(); // Generate a unique key for the announcement
        databaseReference.child(key).setValue(newAnnouncement);

        // Clear the EditText
        announcementEditText.setText("");
    }

    // Add onClick attribute in XML for the post button to call this method
    public void onPostButtonClick(View view) {
        postAnnouncement();
    }
    public void switchToAnnouncementsList(View view) {
        Intent intent = new Intent(this, AnnouncementsListActivity.class);
        startActivity(intent);
    }
    public void backToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
