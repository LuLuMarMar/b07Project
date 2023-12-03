package com.example.b07_project.presenter;

import com.example.b07_project.RegisterActivity;
import com.example.b07_project.model.RegisterModel;
import com.example.b07_project.model.RegisterModelImpl;
import com.example.b07_project.view.RegisterView;

public class RegisterPresenter {
    private RegisterModelImpl model;
    private RegisterView view;

    public RegisterPresenter(RegisterModelImpl model, RegisterView view) {
        this.model = model;
        this.view = view;
    }

    public void CreateUserAndPassword(String email, String password, boolean isAdmin) {
        model.createUserAndPass(email, password, new RegisterModel.OnRegisterListener() {
            @Override
            public void OnAdminRegister(boolean isAdmin) {
                view.ShowAdminRegisterSuccess(isAdmin);
            }

            @Override
            public void OnStudentRegister() {
                view.ShowStudentRegisterSuccess();
            }

            @Override
            public void OnFailedRegister(){
                view.ShowRegisterFailed();
            }
        });
    }
}
