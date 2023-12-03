package com.example.b07_project.model;

public interface RegisterModel {
    interface OnRegisterListener {
        void OnAdminRegister(boolean isAdmin);
        void OnStudentRegister();
        void OnFailedRegister();
    }
    void createUserAndPass(String email, String pass, OnRegisterListener listener);



}
