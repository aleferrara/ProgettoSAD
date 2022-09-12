package com.example.mwng.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mwng.repository.FirebaseAuthDB;
import com.example.mwng.repository.ReservationDB;
import com.example.mwng.view.ReservationFragment;

import java.util.Date;

public class ReservationViewModel extends AndroidViewModel {

    private static final String m1 = "8.30";
    private static final String m2 = "10.30";
    private static final String p1 = "15.00";
    private static final String p2 = "17.00";
    public ReservationDB dbRef;
    public FirebaseAuthDB mAuth;
    public MutableLiveData<Boolean> isBookedUp;

    public ReservationViewModel(@NonNull Application application) {

        super(application);
        mAuth = new FirebaseAuthDB(application);
        dbRef = new ReservationDB();
        isBookedUp = dbRef.availableMutableLiveData;

    }

    public String onSelection(int slot, int day, int month, int year){

        String date = day+ "/" + month + "/" + year;
        switch (slot) {
            case 1:
                book(date, m1);
                break;
            case 2:
                book(date, m2);
                break;
            case 3:
                book(date, p1);
                break;
            case 4:
                book(date, p2);
                break;
        }

        return date;

    }

    public void book(String date, String timeSlot) {
        Log.i("Date selected", date + " " + timeSlot);
        dbRef.book(date, timeSlot, mAuth.getUserID());
    }

    public MutableLiveData<Boolean> getBookedUp(){
        return isBookedUp;
    }

    public String getSlot(int i) {
        String slot = "null";
        switch (i) {
            case 1:
                slot = m1;
                break;
            case 2:
                slot = m2;
                break;
            case 3:
                slot = p1;
                break;
            case 4:
                slot = p2;
                break;
        }
        return slot;
    }

}