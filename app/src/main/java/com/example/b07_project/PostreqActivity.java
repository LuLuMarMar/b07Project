package com.example.b07_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PostreqActivity extends AppCompatActivity {

    private RadioGroup radioGroupLevel, radioGroupCredits;
    private Button btnSubmit;
    private EditText editTextCourse1, editTextCourse2, editTextCourse3, editTextCourse4, editTextCourse5;
    private String selectedLevel, selectedCredits;
    private double gradeCourse1, gradeCourse2, gradeCourse3, gradeCourse4, gradeCourse5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postreq);

        radioGroupLevel = findViewById(R.id.radioGroupLevel);
        radioGroupCredits = findViewById(R.id.radioGroupCredits);
        btnSubmit = findViewById(R.id.btnSubmit);
        editTextCourse1 = findViewById(R.id.editTextCourse1);
        editTextCourse2 = findViewById(R.id.editTextCourse2);
        editTextCourse3 = findViewById(R.id.editTextCourse3);
        editTextCourse4 = findViewById(R.id.editTextCourse4);
        editTextCourse5 = findViewById(R.id.editTextCourse5);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveValues();
                showRequirements();
            }
        });

        radioGroupLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedLevel = selectedRadioButton.getText().toString();
            }
        });

        radioGroupCredits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedCredits = selectedRadioButton.getText().toString();
            }
        });
    }

    private void saveValues() {
        // Save the entered grades in variables
        gradeCourse1 = getGradeFromEditText(editTextCourse1);
        gradeCourse2 = getGradeFromEditText(editTextCourse2);
        gradeCourse3 = getGradeFromEditText(editTextCourse3);
        gradeCourse4 = getGradeFromEditText(editTextCourse4);
        gradeCourse5 = getGradeFromEditText(editTextCourse5);
    }

    private double getGradeFromEditText(EditText editText) {
        String gradeString = editText.getText().toString();
        if (!gradeString.isEmpty()) {
            try {
                return Double.parseDouble(gradeString);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0.0; // Default value if the grade is not entered or invalid
    }

    private void showRequirements() {
       double avg = (gradeCourse1 + gradeCourse2 + gradeCourse3+ gradeCourse4 + gradeCourse5)/5;

        // Example: Display the saved values in the TextView
        TextView tvRequirements = findViewById(R.id.tvRequirements);
        tvRequirements.setText(selectedLevel);

//        if (selectedLevel == "Minor"){
//            if (gradeCourse5 >= 0.7 && gradeCourse4 >= 0.7 &&gradeCourse3 >= 0.7 &&gradeCourse2 >= 0.7 &&gradeCourse1 >= 0.7 && selectedCredits=="Yes"){
//                tvRequirements.setText("You qualify for Computer Science POSt Minor.");
//            }
//        }
//
//        else {
//            if (gradeCourse5 >= 0.7 && gradeCourse4 >= 0.7 &&gradeCourse3 >= 0.7 &&gradeCourse2 >= 0.7 &&gradeCourse1 >= 0.7 && selectedCredits=="Yes" && avg>=2.5 && gradeCourse2>=3.0 && (gradeCourse5>2.3&&gradeCourse4>2.3 || gradeCourse5>2.3&&gradeCourse1>2.3|| gradeCourse4>2.3&&gradeCourse1>2.3)){
//                tvRequirements.setText("You qualify for Computer Science POSt Major / Specialist.");
//            }
//
//            else{
//                tvRequirements.setText("You do not qualify for Computer Science POSt.");
//            }
//        }

    }
}