package com.example.mwng.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mwng.model.Cat;
import com.example.mwng.repository.FirebaseAuthDB;
import com.example.mwng.repository.UserListDB;

import java.util.ArrayList;

public class CatProfileViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isInUserList;
    private UserListDB userListDB;
    private FirebaseAuthDB firebaseAuthDB;
    private ArrayList<Cat> catArrayList;

    public CatProfileViewModel(@NonNull Application application) {
        super(application);

        isInUserList = new MutableLiveData<>();
        firebaseAuthDB = new FirebaseAuthDB(this.getApplication());
        userListDB = new UserListDB(firebaseAuthDB.getUserID());
        catArrayList = new ArrayList<>();
        catArrayList = userListDB.getArrayListMutableLiveData().getValue();
//        Log.i("size in viewmodel", String.valueOf(catArrayList.size()));

    }

    public boolean isInUserList(String key){
        Log.i("lista utente", String.valueOf(catArrayList.size()));
        if (existsInList(catArrayList, key)) {
            return true;
        }
        return false;
    }

    boolean existsInList(ArrayList<Cat> list, String chiave){
        boolean exists = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getChiave() == chiave)
                exists = true;
        }
        return exists;
    }

}