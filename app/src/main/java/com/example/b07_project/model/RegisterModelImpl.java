package com.example.b07_project.model;

import android.text.TextUtils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterModelImpl implements RegisterModel{

    @Override
    public void createUserAndPass(String email, String pass, OnRegisterListener listener) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(task -> {
                        boolean isAdmin = AssignAdmin(email);
                        if (task.isSuccessful()) { // Successful Admin Register
                            listener.OnAdminRegister(isAdmin);
                        } else if (task.isSuccessful()  && !(AssignAdmin(email))) { //Successful Student Register
                            listener.OnStudentRegister();
                        } else {
                            listener.OnFailedRegister();
                        }
                    });
        }
    }

    boolean AssignAdmin(String email){
        if(email.contains("@adminmail")){
            return true;
        } else {
            return false;
        }
    }
}
