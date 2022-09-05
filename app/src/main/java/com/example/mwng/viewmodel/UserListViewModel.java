package com.example.mwng.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mwng.model.Cat;
import com.example.mwng.repository.FirebaseAuthDB;
import com.example.mwng.repository.MainListDB;
import com.example.mwng.repository.UserListDB;

import java.util.ArrayList;

public class UserListViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Cat>> catArrayList;
    private UserListDB userListDB;
    private FirebaseAuthDB firebaseAuthDB;

    public UserListViewModel(@NonNull Application application) {
        super(application);

        firebaseAuthDB = new FirebaseAuthDB(application);
        userListDB = new UserListDB(firebaseAuthDB.getUserID());
        catArrayList = userListDB.getArrayListMutableLiveData();
    }

    public MutableLiveData<ArrayList<Cat>> getCatArrayList() {
        return catArrayList;
    }

    public Bundle onListItemClicked(AdapterView adapterView, View view, int i){
        Cat cat = (Cat) adapterView.getItemAtPosition(i);
        Bundle data = new Bundle();
        data.putString("nome", cat.getNome());
        data.putString("eta", cat.getEta());
        data.putString("sesso", cat.getSesso());
        data.putString("razza", cat.getRazza());
        data.putString("image", cat.getImage());
        data.putString("chiave", cat.getChiave());
        return data;
    }

}