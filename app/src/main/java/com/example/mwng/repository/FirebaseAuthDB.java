package com.example.mwng.repository;

import android.app.Application;
import android.content.Context;
import android.media.AsyncPlayer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthDB {

    private Application application;
    private FirebaseAuth mAuth;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;

    public FirebaseAuthDB(Application application) {
        this.application = application;
        mAuth = FirebaseAuth.getInstance();
        firebaseUserMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();
        if (mAuth.getCurrentUser() != null){
            firebaseUserMutableLiveData.setValue(mAuth.getCurrentUser());
        }
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUserMutableLiveData.setValue(mAuth.getCurrentUser());
                    userLoggedMutableLiveData.setValue(true);
                } else {
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signOut(){
        mAuth.signOut();
        userLoggedMutableLiveData.setValue(false);
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public String getUserID (){
        return mAuth.getCurrentUser().getUid();
    }

}
