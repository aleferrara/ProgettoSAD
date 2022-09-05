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

public class UserListDB {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userListReference;
    private DatabaseReference catsReference;
    private ArrayList<Cat> catArrayList;
    private MutableLiveData<ArrayList<Cat>> arrayListMutableLiveData;
    private String userID;

    public UserListDB(String userID) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        this.userID = userID;
        userListReference = firebaseDatabase.getReference("Users").child(userID).child("Lista");
        arrayListMutableLiveData = new MutableLiveData<>();
        catArrayList = new ArrayList<>();

        userListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){

                    String key = ds.getKey();
                    Log.i("chiave", key);
                    catsReference = FirebaseDatabase.getInstance().getReference().child("Ospiti").child(key);
                    catsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String nome = snapshot.child("nome").getValue().toString();
                            String eta = snapshot.child("eta").getValue(String.class);
                            String sesso = snapshot.child("sesso").getValue(String.class);
                            String razza = snapshot.child("razza").getValue(String.class);
                            String imageUrl = snapshot.child("imageUrl").getValue(String.class);
                            String chiave = snapshot.getKey();
                            Cat cat = new Cat(nome, eta, sesso, razza, imageUrl, chiave);
                            String nometest = cat.getNome();
                            Log.i("testnome", nometest);
                            catArrayList.add(cat);
                            String asize = String.valueOf(catArrayList.size());
                            Log.i("arraysize", asize);
                            arrayListMutableLiveData.setValue(catArrayList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

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
