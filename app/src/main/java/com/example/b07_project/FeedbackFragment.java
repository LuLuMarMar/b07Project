package com.example.b07_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        final TextInputEditText textInput = findViewById(R.id.comment_text_input);
        addFeedbackToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = textInput.getText().toString();
                Feedback feedback = new Feedback(comment, 0);
                feedbackReference.child("name_of_event").setValue(feedback);
            }
        });
    }
}
