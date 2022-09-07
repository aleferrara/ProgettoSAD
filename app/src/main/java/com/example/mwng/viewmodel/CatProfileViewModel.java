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
import java.util.Objects;

public class CatProfileViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Cat>> catArrayList;
    private UserListDB userListDB;
    private FirebaseAuthDB firebaseAuthDB;

    public CatProfileViewModel(@NonNull Application application) {
        super(application);

        firebaseAuthDB = new FirebaseAuthDB(application);
        userListDB = new UserListDB(firebaseAuthDB.getUserID());
        catArrayList = userListDB.getArrayListMutableLiveData();
    }

    public MutableLiveData<ArrayList<Cat>> getCatArrayList() {
        return catArrayList;
    }

    public boolean isInUserList(ArrayList<Cat> catArrayList, String chiave){
        for (int i = 0; i < catArrayList.size(); i++){
            if (Objects.equals(catArrayList.get(i).getChiave(), chiave))
                return true;
        }
        return false;
    }

    public void addCat(String key){
        userListDB.addCat(key);
    }

    public void removeCat(String key){
        userListDB.removeCat(key);
    }

}