package com.example.mwng.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.mwng.repository.FirebaseAuthDB;

import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {

    private FirebaseAuthDB mAuth;
    private MutableLiveData<FirebaseUser> user;
    private MutableLiveData<Boolean> loggedIn;
    public MutableLiveData<String> email;
    public MutableLiveData<String> password;
    private SharedPreferences sharedPreferences;

    public LoginViewModel(@NonNull Application application) {

        super(application);
        mAuth = new FirebaseAuthDB(application);
        user = mAuth.getFirebaseUserMutableLiveData();
        loggedIn = mAuth.getUserLoggedMutableLiveData();
        email = new MutableLiveData<>();
        password = new MutableLiveData<>();

    }

    public void onLoginClicked(){
        signIn(email.getValue(), password.getValue());
    }

    public void signIn(String email, String password){
        mAuth.signIn(email, password);
    }

    public MutableLiveData<FirebaseUser> getUser() {
        return user;
    }

    public MutableLiveData<Boolean> getLoggedIn() {
        return loggedIn;
    }
}
