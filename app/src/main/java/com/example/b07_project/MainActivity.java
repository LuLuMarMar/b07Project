package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_project.model.LoginModelImpl;
import com.example.b07_project.presenter.LoginPresenter;
import com.example.b07_project.view.LoginView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements LoginView {
    private EditText editEmailText, passwordEditText;
    private Button btnSignIn, btnRegister;
    private Switch switchStatus;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmailText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);

        switchStatus = findViewById(R.id.switchStatus);

        presenter = new LoginPresenter(new LoginModelImpl(), this);

        btnSignIn.setOnClickListener(v -> {
            String email = editEmailText.getText().toString();
            String pass = passwordEditText.getText().toString();
            boolean isAdmin = switchStatus.isChecked();
            presenter.AuthenticateUser(email, pass, isAdmin);
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //Inherited from LoginView
    @Override
    public void showLoginError() {
        Toast.makeText(this, "Login Failed, Please enter the correct credentials...", Toast.LENGTH_SHORT).show();
    }

    //Inherited from LoginView
    @Override
    public void showLoginSuccess(boolean isAdmin) {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        if (isAdmin) {
            Toast.makeText(MainActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
            //Send to HomeActivity
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Student login successful", Toast.LENGTH_SHORT).show();
            //Send to HomeActivity
            startActivity(intent);
        }
    }
}