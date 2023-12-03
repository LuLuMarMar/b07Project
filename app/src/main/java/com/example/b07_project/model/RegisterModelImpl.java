package com.example.b07_project.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterModelImpl implements RegisterModel{

    @Override
    public void createUserAndPass(String email, String pass, OnRegisterListener listener){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
         .addOnCompleteListener(task -> {
            if (task.isSuccessful() && (email != null) && (pass != null)) { // Successful Admin Register
                boolean isAdmin = AssignAdmin(email);
                listener.OnAdminRegister(isAdmin);
            } else if (task.isSuccessful() && !(AssignAdmin(email)) && (email != null) && (pass != null)){ //Successful Student Register
                listener.OnStudentRegister();
            } else {
                listener.OnFailedRegister();
            }
        });
    }

    boolean AssignAdmin(String email){
        if(email.contains("@admin_mail")){
            return true;
        } else {
            return false;
        }
    }
}
