package com.example.b07_project;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFeedbackActivity extends AppCompatActivity {
    private DatabaseReference feedbackReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_feedback);
        Intent intent = getIntent();
        String name = intent.getStringExtra("eventName");
        feedbackReference = FirebaseDatabase.getInstance().getReference("feedback");
        Button addFeedbackToDB = findViewById(R.id.feedback_button);
        ImageButton btnExit = findViewById(R.id.btnExit);
        addFeedbackToDB.setBackgroundColor(Color.parseColor("#007FA3"));
        RadioGroup ratingGroup = findViewById(R.id.ratingGroup);
        TextInputEditText commentInput = findViewById(R.id.comment_text_input);
        TextView fbText = findViewById(R.id.feedbackText);
        fbText.setText("How was your experience in \n" + name);
        addFeedbackToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = feedbackReference.push().getKey();
                String comment = commentInput.getText().toString();
                int rating = ratingGroup.getCheckedRadioButtonId();
                if(rating != -1) {
                    RadioButton ratingButton = findViewById(rating);
                    rating = Integer.parseInt(ratingButton.getText().toString());
                } else {
                    rating = 0;
                }
                Feedback feedback = new Feedback(rating, comment, name);
                feedbackReference.child(key).setValue(feedback);
                Toast.makeText(AddFeedbackActivity.this,
                        "Thank you for your feedback", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
