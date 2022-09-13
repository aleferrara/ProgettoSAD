package com.example.mwng.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.mwng.R;
import com.example.mwng.viewmodel.ReservationViewModel;
import com.example.mwng.databinding.FragmentReservationBinding;

public class ReservationFragment extends Fragment implements View.OnClickListener {

    private ReservationViewModel mViewModel;
    private DatePicker datePicker;
    private FragmentReservationBinding binding;
    private Button m1, m2, p1, p2;
    private int slot;
    private String date;
    private boolean dialogShown;

    public static ReservationFragment newInstance() {
        return new ReservationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentReservationBinding.inflate(inflater);
        mViewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
        binding.setReservationViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        dialogShown = false;

        datePicker = binding.calendar;
        m1 = binding.matt1;
        m1.setOnClickListener(this);
        m2 = binding.matt2;
        m2.setOnClickListener(this);
        p1 = binding.pom1;
        p1.setOnClickListener(this);
        p2 = binding.pom2;
        p2.setOnClickListener(this);

        datePicker.setMinDate(System.currentTimeMillis());

        return binding.getRoot();

    }

    @Override
    public void onClick(View view) {
        slot = 0;
        date = "null";
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        switch (view.getId()) {
            case R.id.matt1:
                date = mViewModel.onSelection(1, day, month, year);
                slot = 1;
                break;
            case R.id.matt2:
                date = mViewModel.onSelection(2, day, month, year);
                slot = 2;
                break;
            case R.id.pom1:
                date = mViewModel.onSelection(3, day, month, year);
                slot = 3;
                break;
            case R.id.pom2:
                date = mViewModel.onSelection(4, day, month, year);
                slot = 4;
                break;
        }
        mViewModel.getBookedUp().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!dialogShown) {
                    dialogShown = true;
                    if (aBoolean) {
                        Log.i("UI", "Effettuata");
                        new AlertDialog.Builder(getContext())
                                .setTitle("Prenotazione aggiunta!").setIcon(R.drawable.ic_done)
                                .setMessage("Appuntamento alle " + mViewModel.getSlot(slot) + " del " + date)
                                .setPositiveButton("Okay!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        dialogShown = false;
                                    }
                                }).show();
                    } else {
                        Log.i("UI", "Non effettuata");
                        new AlertDialog.Builder(getContext())
                                .setTitle("Impossibile prenotare.").setIcon(R.drawable.ic_done)
                                .setMessage("Non c'è disponibilità per le " + mViewModel.getSlot(slot) + " del " + date)
                                .setPositiveButton("Okay!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        dialogShown = false;
                                    }
                                }).show();
                    }
                }
            }
        });
    }

}