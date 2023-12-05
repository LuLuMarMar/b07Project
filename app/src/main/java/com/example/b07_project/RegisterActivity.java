package com.example.b07_project;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText, passwordConfirmEditText;
    private Switch adminRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.editEmailRegister);
        passwordEditText = findViewById(R.id.editPasswordRegister);
        passwordConfirmEditText = findViewById(R.id.editPasswordConfirm);
        adminRegister = findViewById(R.id.switchAdmin);
        Button registerButton = findViewById(R.id.btnAccountRegister);
        Button backToLoginButton = findViewById(R.id.btnBacktoLogin);

        registerButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String passwordConfirm = passwordConfirmEditText.getText().toString();
            boolean isAdmin = adminRegister.isChecked();

            if(!password.equals(passwordConfirm)) {
                Toast.makeText(RegisterActivity.this,
                        "Password not matching.", Toast.LENGTH_SHORT).show();
             } else if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                registerUser(email, password, isAdmin);
            } else {
                Toast.makeText(RegisterActivity.this,
                        "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        backToLoginButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void registerUser(String email, String password, boolean isAdmin) {
        DatabaseReference userDataReference = FirebaseDatabase.getInstance()
                .getReference("user_data");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration success
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                String userUID = user.getUid();
                                userDataReference.child(userUID)
                                        .child("admin").setValue(isAdmin);
                            }
                            Toast.makeText(RegisterActivity.this,
                                    "Registration successful", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // If registration fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this,
                                    "Registration failed. "
                                            + Objects.requireNonNull(task.getException())
                                            .getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

