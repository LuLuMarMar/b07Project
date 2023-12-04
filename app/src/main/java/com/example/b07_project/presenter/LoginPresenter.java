package com.example.b07_project.presenter;

import com.example.b07_project.model.LoginModel;
import com.example.b07_project.model.LoginModelImpl;
import com.example.b07_project.view.LoginView;

public class LoginPresenter {
    private LoginModelImpl model;
    private LoginView view;

    public LoginPresenter(LoginModelImpl model, LoginView view){
        this.model = model;
        this.view = view;
    }

    public void AuthenticateUser(String email, String password) {
        model.authenticateUser(email, password, new LoginModel.OnLoginFinishedListener() {
            @Override
            public void onLoginSuccess(boolean isAdmin) {view.showLoginSuccess(isAdmin);}
            @Override
            public void onLoginError() {view.showLoginError();}
        });

    }
}
