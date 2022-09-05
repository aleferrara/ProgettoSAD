package com.example.mwng.repository;

import com.example.mwng.model.Reservation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReservationDB {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reservationsReference;

    public ReservationDB(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        reservationsReference = firebaseDatabase.getReference("Reservations");
    }

    public void book(String date, String slot, String userID){
        DatabaseReference reservation = reservationsReference.push();
        reservation.setValue(new Reservation(date, slot, userID));
    }

}
