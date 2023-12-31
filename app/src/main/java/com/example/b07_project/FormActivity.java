package com.example.b07_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import android.widget.Toast;
import android.graphics.Color;

public class FormActivity extends AppCompatActivity {

    // Firebase
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("events");

        final EditText editTextNameForm = findViewById(R.id.editTextNameForm);
        final DatePicker datePickerForm = findViewById(R.id.datePickerForm);
        final EditText editTextLimitForm = findViewById(R.id.editTextLimitForm);
        Button btnSubmitForm = findViewById(R.id.btnSubmitForm);

        // Back button to return to MainActivity
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setBackgroundColor(Color.BLUE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the current activity and go back to the main page
                finish();
            }
        });

        btnSubmitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("message");
//
//                myRef.setValue("Hello,ooo World!");
                // Get values from the form
                String name = editTextNameForm.getText().toString();
                int day = datePickerForm.getDayOfMonth();
                int month = datePickerForm.getMonth() + 1; // Month is zero-based
                int year = datePickerForm.getYear();
                String participant_limit = editTextLimitForm.getText().toString();

                if (name.isEmpty() || participant_limit.isEmpty()) {
                    // Show an error message if any field is empty
                    Toast.makeText(FormActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return; // Do not proceed further
                }

                int limit = Integer.parseInt(participant_limit);
                // Create a data object
                UserData userData = new UserData(name, day, month, year,limit);


                String key = databaseReference.push().getKey(); // Generate a unique key for the announcement
                databaseReference.child(key).setValue(userData);

                // Show a confirmation message
                Toast.makeText(FormActivity.this, "Done!", Toast.LENGTH_SHORT).show();

                // Show a notification
                NotificationHelper.showEventNotification(
                        FormActivity.this,
                        name,
                        "Event Date: " + day + "/" + month + "/" + year + "\nParticipant Limit: " + limit);



                // Finish the current activity and go back to the main page
                finish();
            }
        });
    }
}