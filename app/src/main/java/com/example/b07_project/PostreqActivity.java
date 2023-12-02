package com.example.b07_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PostreqActivity extends AppCompatActivity {

    private RadioGroup radioGroupLevel, radioGroupCredits;
    private Button btnSubmit, btnBack;
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
        btnBack = findViewById(R.id.btnBack);
        editTextCourse1 = findViewById(R.id.editTextCourse1);
        editTextCourse2 = findViewById(R.id.editTextCourse2);
        editTextCourse3 = findViewById(R.id.editTextCourse3);
        editTextCourse4 = findViewById(R.id.editTextCourse4);
        editTextCourse5 = findViewById(R.id.editTextCourse5);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidForm()) {
                    saveValues();
                    showRequirements();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

    private boolean isValidForm() {
        if (radioGroupLevel.getCheckedRadioButtonId() == -1 ||
                radioGroupCredits.getCheckedRadioButtonId() == -1 ||
                !isValidGrade(editTextCourse1.getText().toString()) ||
                !isValidGrade(editTextCourse2.getText().toString()) ||
                !isValidGrade(editTextCourse3.getText().toString()) ||
                !isValidGrade(editTextCourse4.getText().toString()) ||
                !isValidGrade(editTextCourse5.getText().toString())) {
            Toast.makeText(PostreqActivity.this, "Please fill in all fields and enter valid grades (0.0-4.0).", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
                double grade = Double.parseDouble(gradeString);
                if (grade >= 0 && grade <= 4) {
                    return grade;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0.0;
    }

    private boolean isValidGrade(String grade) {
        if (!grade.isEmpty()) {
            try {
                double numericGrade = Double.parseDouble(grade);
                return numericGrade >= 0 && numericGrade <= 4;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    private void resetValues() {
        radioGroupLevel.clearCheck();
        radioGroupCredits.clearCheck();
        editTextCourse1.setText("");
        editTextCourse2.setText("");
        editTextCourse3.setText("");
        editTextCourse4.setText("");
        editTextCourse5.setText("");
    }

    private void showRequirements() {
        double avg = (gradeCourse1 + gradeCourse2 + gradeCourse3 + gradeCourse4 + gradeCourse5) / 5;

        TextView tvRequirements = findViewById(R.id.tvRequirements);

        if (selectedLevel.equals("Minor")) {
            if (gradeCourse5 >= 0.7 && gradeCourse4 >= 0.7 && gradeCourse3 >= 0.7 && gradeCourse2 >= 0.7 && gradeCourse1 >= 0.7 && selectedCredits.equals("Yes")) {
                tvRequirements.setText("You qualify for Computer Science POSt Minor.");
                tvRequirements.setBackgroundColor(getResources().getColor(R.color.light_green));
            }
            else {
                tvRequirements.setText("You do not qualify for Computer Science POSt " + selectedLevel);
                tvRequirements.setBackgroundColor(getResources().getColor(R.color.light_red));
            }
        } else {
            if (gradeCourse5 >= 0.7 && gradeCourse4 >= 0.7 && gradeCourse3 >= 0.7 && gradeCourse2 >= 0.7 && gradeCourse1 >= 0.7 && selectedCredits.equals("Yes") && avg >= 2.5 && gradeCourse2 >= 3.0 && (gradeCourse5 > 1.7 && gradeCourse4 > 1.7 || gradeCourse5 > 1.7 && gradeCourse1 > 1.7 || gradeCourse4 >1.7 && gradeCourse1 > 1.7)) {
                tvRequirements.setText("You qualify for Computer Science POSt " + selectedLevel);
                tvRequirements.setBackgroundColor(getResources().getColor(R.color.light_green));
            }
            else {
                tvRequirements.setText("You do not qualify for Computer Science POSt " + selectedLevel);
                tvRequirements.setBackgroundColor(getResources().getColor(R.color.light_red));
            }

//            radioGroupLevel.clearCheck();
//            radioGroupCredits.clearCheck();
//            editTextCourse1.setText("");
//            editTextCourse2.setText("");
//            editTextCourse3.setText("");
//            editTextCourse4.setText("");
//            editTextCourse5.setText("");
        }

    }
}