package com.example.mwng.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mwng.model.Cat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainListDB {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference listReference;
    private ArrayList<Cat> catArrayList;
    private MutableLiveData<ArrayList<Cat>> arrayListMutableLiveData;

    public MainListDB(){

        firebaseDatabase = FirebaseDatabase.getInstance();
        listReference = firebaseDatabase.getReference("Ospiti");
        arrayListMutableLiveData = new MutableLiveData<>();
        catArrayList = new ArrayList<>();

        listReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String ID = ds.child("Id").getValue(String.class);
                    String nome = ds.child("nome").getValue(String.class);
                    String eta = ds.child("eta").getValue(String.class);
                    String sesso = ds.child("sesso").getValue(String.class);
                    String razza = ds.child("razza").getValue(String.class);
                    String imageUrl = ds.child("imageUrl").getValue(String.class);
                    String chiave = ds.getKey();
                    Cat cat = new Cat(ID, nome, eta, sesso, razza, imageUrl, chiave);
                    catArrayList.add(cat);
                }
                arrayListMutableLiveData.setValue(catArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public MutableLiveData<ArrayList<Cat>> getArrayListMutableLiveData(){
        return arrayListMutableLiveData;
    }

}
