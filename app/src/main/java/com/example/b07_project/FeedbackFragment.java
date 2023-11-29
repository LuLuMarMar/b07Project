package com.example.b07_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        RadioGroup ratingGroup = findViewById(R.id.ratingGroup);
        TextInputEditText textInput = findViewById(R.id.comment_text_input);
        addFeedbackToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = textInput.getText().toString();
                int rating = ratingGroup.getCheckedRadioButtonId();
                if(rating != -1) {
                    RadioButton ratingButton = findViewById(rating);
                    rating = Integer.parseInt(ratingButton.getText().toString());
                } else {
                    rating = 0;
                }
                Feedback feedback = new Feedback(comment, rating);
                feedbackReference.child("name_of_event").setValue(feedback);
            }
        });
    }
}
