package com.example.b07_project.view;

/*
View: Handle inputs/outputs and show Data (in this case the status of login success)

Should show success or failure of the Login

 */
public interface LoginView {
    public void showLoginSuccess(boolean isAdmin);
    public void showLoginError();
}
