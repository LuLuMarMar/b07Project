package com.example.b07_project.model;

import com.google.android.gms.common.api.internal.RegisterListenerMethod;

public interface LoginModel {
    interface OnLoginFinishedListener{
        void onLoginSuccess(boolean isAdmin);
        void onLoginError();
    }
    void authenticateUser(String email, String password, OnLoginFinishedListener listener);

}
