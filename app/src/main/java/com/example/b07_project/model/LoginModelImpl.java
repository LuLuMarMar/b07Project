package com.example.b07_project.model;


import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.b07_project.HomeActivity;
import com.example.b07_project.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginModelImpl implements LoginModel {
    @Override
    public void authenticateUser(String email, String pass, OnLoginFinishedListener listener) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Authentication successful
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                listener.onLoginSuccess();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            listener.onLoginError();
                        }
                    });
        }
    }
}
