package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PostreqActivity extends AppCompatActivity {

    private RadioGroup radioGroupLevel;
    private RadioGroup radioGroupProgram;
    private Button btnSubmit;
    private TextView tvRequirements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postreq);

        radioGroupLevel = findViewById(R.id.radioGroupLevel);
        radioGroupProgram = findViewById(R.id.radioGroupProgram);
        btnSubmit = findViewById(R.id.btnSubmit);
        tvRequirements = findViewById(R.id.tvRequirements);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRequirements();
            }
        });
    }

    private void showRequirements() {
        String level = getSelectedRadioButtonText(radioGroupLevel);
        String program = getSelectedRadioButtonText(radioGroupProgram);

        // Determine requirements based on level and program
        String requirements = getRequirements(level, program);

        // Display requirements in the TextView
        tvRequirements.setText(requirements);
    }

    private String getSelectedRadioButtonText(RadioGroup radioGroup) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            return selectedRadioButton.getText().toString();
        }
        return "";
    }

    private String getRequirements(String level, String program) {
        // You can customize this method to provide specific requirements
        // based on the combination of level and program selected.
        // This is a simple example; you can replace it with your logic.
        return "Requirements for " + level + " in " + program;
    }
}