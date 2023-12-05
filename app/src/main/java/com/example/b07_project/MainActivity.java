package com.example.b07_project;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_project.model.LoginModelImpl;
import com.example.b07_project.presenter.LoginPresenter;
import com.example.b07_project.view.LoginView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements LoginView {
    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText editEmailText, passwordEditText;
    private Button btnSignIn, btnRegister;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editEmailText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
        btnSignIn.setBackgroundColor(Color.parseColor("#007FA3"));
        btnRegister.setBackgroundColor(Color.parseColor("#007FA3"));

        presenter = new LoginPresenter(new LoginModelImpl(), this);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };

        btnSignIn.setOnClickListener(v -> {
            String pass = passwordEditText.getText().toString();
            String email = editEmailText.getText().toString();
            presenter.AuthenticateUser(email, pass);
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void showLoginSuccess(){
        Toast.makeText(MainActivity.this, "Login Successful",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoginError(String email, String pass){
        if(!email.equals("") && !pass.equals("") ){
            Toast.makeText(MainActivity.this,
                    "Authentication failed. Incorrect credentials.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,
                    "Please fill out all the fields.", Toast.LENGTH_SHORT).show();
        }
    }

}