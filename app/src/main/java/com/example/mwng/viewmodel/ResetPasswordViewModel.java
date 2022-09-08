package com.example.mwng.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mwng.repository.FirebaseAuthDB;

public class ResetPasswordViewModel extends AndroidViewModel {

    private FirebaseAuthDB mAuth;
    private MutableLiveData<Boolean> emailSent;
    private MutableLiveData<String> errorMessage;

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);

        mAuth = new FirebaseAuthDB(application);
        emailSent = new MutableLiveData<>();
        emailSent = mAuth.getEmailSentMutableLiveData();
        errorMessage = new MutableLiveData<>();
        errorMessage = mAuth.getErrorMessageMutableLiveData();

    }

    public void resetPassword(String email){
        mAuth.resetPassword(email);
    }

    public MutableLiveData<Boolean> getEmailSent(){
        return emailSent;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
