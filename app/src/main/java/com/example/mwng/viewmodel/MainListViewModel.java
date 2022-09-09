package com.example.mwng.viewmodel;

import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.mwng.model.Cat;
import com.example.mwng.repository.MainListDB;

import java.util.ArrayList;

public class MainListViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Cat>> catArrayList;
    private MainListDB mainListDB;

    public MainListViewModel(@NonNull Application application) {
        super(application);

        mainListDB = new MainListDB();
        catArrayList = mainListDB.getArrayListMutableLiveData();

    }

    public MutableLiveData<ArrayList<Cat>> getCatArrayList() {
        return catArrayList;
    }

    public Bundle onListItemClicked(AdapterView adapterView, int i){
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