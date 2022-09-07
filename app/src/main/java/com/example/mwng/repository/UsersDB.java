package com.example.mwng.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mwng.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsersDB {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDatabase;
    private MutableLiveData<Boolean> userRegisteredMutableLiveData;

    public UsersDB(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        userDatabase = firebaseDatabase.getReference("Users");
        userRegisteredMutableLiveData = new MutableLiveData<>();

    }

    public void addUser(User user, String ID) {

        userDatabase.child(ID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    userRegisteredMutableLiveData.setValue(true);
                } else {
                    userRegisteredMutableLiveData.setValue(false);
                }
            }
        });
    }

    public MutableLiveData<Boolean> getUserRegisteredMutableLiveData() {
        return userRegisteredMutableLiveData;
    }

}
