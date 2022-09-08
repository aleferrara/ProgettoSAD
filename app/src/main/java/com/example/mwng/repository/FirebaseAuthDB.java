package com.example.mwng.repository;

import android.app.Application;
import android.content.Context;
import android.media.AsyncPlayer;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mwng.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthDB {

    private Application application;
    private FirebaseAuth mAuth;
    private UsersDB usersDB;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;
    private MutableLiveData<Boolean> userCreatedMutableLiveData;
    private MutableLiveData<Boolean> emailSentMutableLiveData;
    private MutableLiveData<String> errorMessageMutableLiveData;

    public FirebaseAuthDB(Application application) {
        this.application = application;
        mAuth = FirebaseAuth.getInstance();
        firebaseUserMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();
        userCreatedMutableLiveData = new MutableLiveData<>();
        errorMessageMutableLiveData = new MutableLiveData<>();
        emailSentMutableLiveData = new MutableLiveData<>();
        usersDB = new UsersDB();
        if (mAuth.getCurrentUser() != null){
            firebaseUserMutableLiveData.setValue(mAuth.getCurrentUser());
        }
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user.isEmailVerified()) {
                        firebaseUserMutableLiveData.setValue(user);
                        userLoggedMutableLiveData.setValue(true);
                    } else {
                        user.sendEmailVerification();
                        errorMessageMutableLiveData.setValue("email non verificata");
                        userLoggedMutableLiveData.setValue(false);
                    }
                } else {
                    userLoggedMutableLiveData.setValue(false);
                    errorMessageMutableLiveData.setValue(task.getException().getMessage());
                }
            }
        });
    }

    public void signOut(){
        mAuth.signOut();
        userLoggedMutableLiveData.setValue(false);
    }

    public void createUser(String nome, String cognome, String email, String password){
        Log.i("password", password);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("auth", "utente creato");
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                User user = new User(nome, cognome, email);
                                String UserID = getUserID();
                                usersDB.addUser(user, UserID);
                                Log.i("auth", "utente creato ed email inviata");
                                userCreatedMutableLiveData.setValue(true);
                                errorMessageMutableLiveData.setValue("null");
                            } else {
                                userCreatedMutableLiveData.setValue(false);
                                errorMessageMutableLiveData.setValue(task.getException().getMessage());
                                Log.i("auth", "impossibile inviare email");
                                Log.i("auth", task.getException().getMessage());
                            }
                        }
                    });
                } else {
                    userCreatedMutableLiveData.setValue(false);
                    errorMessageMutableLiveData.setValue(task.getException().getMessage());
                    Log.i("auth", "impossibile creare utente");
                    Log.i("auth", task.getException().getMessage());
                }
            }
        });
    }

    public void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    emailSentMutableLiveData.setValue(true);
                } else {
                    emailSentMutableLiveData.setValue(false);
                    errorMessageMutableLiveData.setValue(task.getException().getMessage());
                }
            }
        });
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserCreatedMutableLiveData() {
        return userCreatedMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public MutableLiveData<Boolean> getEmailSentMutableLiveData() {
        return emailSentMutableLiveData;
    }

    public String getUserID (){
        return mAuth.getCurrentUser().getUid();
    }

    public MutableLiveData<String> getErrorMessageMutableLiveData(){
        return errorMessageMutableLiveData;
    }

}