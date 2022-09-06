package com.example.mwng.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.mwng.repository.FirebaseAuthDB;

public class MainViewModel extends AndroidViewModel {

    private final String LOGPREF = "loginSaved";
    private SharedPreferences sharedPreferences;
    private FirebaseAuthDB mAuth;

    public MainViewModel(@NonNull Application application) {

        super(application);
        mAuth = new FirebaseAuthDB(application);

    }

    public void signOut(){
        mAuth.signOut();
    }

}
