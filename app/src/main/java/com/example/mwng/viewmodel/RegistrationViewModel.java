package com.example.mwng.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.mwng.model.User;
import com.example.mwng.repository.FirebaseAuthDB;
import com.example.mwng.repository.UsersDB;

public class RegistrationViewModel extends AndroidViewModel {

    private FirebaseAuthDB mAuth;
    private MutableLiveData<Boolean> userCreated;
    private MutableLiveData<String> errorMessage;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);

        mAuth = new FirebaseAuthDB(application);
        userCreated = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        userCreated = mAuth.getUserCreatedMutableLiveData();
        errorMessage = mAuth.getErrorMessageMutableLiveData();

    }

    public void signUp(String nome, String cognome, String email, String password){
        mAuth.createUser(nome, cognome, email, password);
    }

    public MutableLiveData<Boolean> getUserCreated() {
        return userCreated;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}