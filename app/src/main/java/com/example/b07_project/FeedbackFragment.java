package com.example.b07_project;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackFragment extends AppCompatActivity {

    private DatabaseReference feedbackReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_feedback);
        feedbackReference = FirebaseDatabase.getInstance().getReference("feedback");
        Button addFeedbackToDB = findViewById(R.id.feedback_button);
        addFeedbackToDB.setBackgroundColor(Color.parseColor("#007FA3"));
        RadioGroup ratingGroup = findViewById(R.id.ratingGroup);
        TextInputEditText commentInput = findViewById(R.id.comment_text_input);
        EditText nameInput = findViewById(R.id.eventNameText);
        addFeedbackToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = feedbackReference.push().getKey();
                String comment = commentInput.getText().toString();
                String name = nameInput.getText().toString();
                int rating = ratingGroup.getCheckedRadioButtonId();
                if(rating != -1) {
                    RadioButton ratingButton = findViewById(rating);
                    rating = Integer.parseInt(ratingButton.getText().toString());
                } else {
                    rating = 0;
                }
                if(name.isEmpty()) {
                    Toast.makeText(FeedbackFragment.this,
                            "Please include the name of the event", Toast.LENGTH_SHORT).show();
                } else {
                    Feedback feedback = new Feedback(rating, comment, name);
                    feedbackReference.child(key).setValue(feedback);
                    Toast.makeText(FeedbackFragment.this,
                            "Thank you for your feedback", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
