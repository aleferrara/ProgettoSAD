package com.example.mwng.viewmodel;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class MainViewModel extends AndroidViewModel {

    private final String LOGPREF = "loginSaved";
    private SharedPreferences sharedPreferences;

    public MainViewModel(@NonNull Application application) {

        super(application);
        sharedPreferences = application.getSharedPreferences(LOGPREF, application.MODE_PRIVATE);

    }

}
