package com.example.b07_project.model;


import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
    Performs actual authentication service using firebase
    CheckAdmin handles Admin Logic
 */

public class LoginModelImpl implements LoginModel {
    @Override
    public void authenticateUser(String email, String password, OnLoginFinishedListener listener) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            boolean isAdmin = checkAdmin(user.getUid());
                            listener.onLoginSuccess(isAdmin);
                        } else {
                            //                        String errorMessage = task.getException().getMessage();
                            listener.onLoginError();
                        }
                    });
        }
    }

    private boolean checkAdmin(String email) {
        return email.contains("@adminmail");
    }
}
