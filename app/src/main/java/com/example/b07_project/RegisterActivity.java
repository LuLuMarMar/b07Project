package com.example.b07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07_project.model.RegisterModelImpl;
import com.example.b07_project.presenter.RegisterPresenter;
import com.example.b07_project.view.RegisterView;

public class RegisterActivity extends AppCompatActivity implements RegisterView {
    private Button btnAccountRegister, btnBacktoLogin;
    private EditText editEmailRegister, editPasswordRegister, editConfirmPassword;
    private Switch adminRegister;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnAccountRegister = findViewById(R.id.btnAccountRegister);
        btnBacktoLogin = findViewById(R.id.btnBacktoLogin);

        editEmailRegister = findViewById(R.id.editEmailRegister);
        editPasswordRegister = findViewById(R.id.editPasswordRegister);
        editConfirmPassword = findViewById(R.id.editPasswordConfirm);

        adminRegister = findViewById(R.id.switchAdmin);

        presenter = new RegisterPresenter(new RegisterModelImpl(), this);

        btnAccountRegister.setOnClickListener(v -> {
            String email = editEmailRegister.getText().toString();
            String pass = editPasswordRegister.getText().toString();
            String confirmPass = editConfirmPassword.getText().toString();
            boolean isAdmin = adminRegister.isChecked();

            if(pass.equals(confirmPass)) { // Should probably put this logic in the Model but later :)
                presenter.CreateUserAndPassword(email, pass, isAdmin);
            }
        });

        btnBacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to Login
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void ShowAdminRegisterSuccess(boolean isAdmin) {
        if(isAdmin){
            Toast.makeText(this, "Registration Success, Welcome Admin!", Toast.LENGTH_SHORT).show();
            //Redirect to Login
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);

        } else {
            //Admin is checked but email is not @admin_mail
            ShowRegisterFailed();
        }
    }

    @Override
    public void ShowStudentRegisterSuccess() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        //Redirect to Login
        Toast.makeText(this, "Registration Success, Welcome Student!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    @Override
    public void ShowRegisterFailed() {
        //Display Message and return to login
        Toast.makeText(this, "Registration Failed. Please enter the correct credentials.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }
}

