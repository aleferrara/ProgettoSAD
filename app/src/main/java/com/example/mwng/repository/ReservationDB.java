package com.example.mwng.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mwng.model.Reservation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReservationDB {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reservationsReference;
    public MutableLiveData<Boolean> availableMutableLiveData;

    public ReservationDB(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        reservationsReference = firebaseDatabase.getReference("Reservations");
        availableMutableLiveData = new MutableLiveData<>();
    }

    public void book(String date, String slot, String userID){
        reservationsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int conto = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if ((ds.child("data").getValue().toString().equals(date)) &&
                            (ds.child("timeSlot").getValue().toString().equals(slot))) {
                        conto++;
                    }
                }
                Log.i("Res","Presente " + conto);
                if (conto < 5) {
                    Log.i("Res","Accettata");
                    availableMutableLiveData.setValue(true);
                    DatabaseReference reservation = reservationsReference.push();
                    reservation.setValue(new Reservation(date, slot, userID));
                } else {
                    Log.i("Res","Non accettata");
                    availableMutableLiveData.setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
