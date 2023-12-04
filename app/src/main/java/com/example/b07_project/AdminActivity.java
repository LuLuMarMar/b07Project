package com.example.b07_project;

import android.content.Intent;
import android.graphics.Color;
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

public class AdminActivity extends AppCompatActivity implements LoginView {
    private EditText editEmailText, passwordEditText;
    private Button btnSignIn, btnRegister;
    private Switch switchStatus;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        editEmailText = findViewById(R.id.editTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextPassword);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn.setBackgroundColor(Color.parseColor("#007FA3"));
        btnRegister.setBackgroundColor(Color.parseColor("#007FA3"));

        switchStatus = findViewById(R.id.switchStatus);

        presenter = new LoginPresenter(new LoginModelImpl(),this);

        btnSignIn.setOnClickListener(v -> {
            String email = editEmailText.getText().toString();
            String pass = passwordEditText.getText().toString();
            presenter.AuthenticateUser(email, pass);
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        switchStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, MainActivity.class);
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
        if(isAdmin) {
            Intent intent = new Intent(AdminActivity.this, HomeActivity.class);
            Toast.makeText(AdminActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
            //Send to HomeActivity
            startActivity(intent);
        }
    }
}
