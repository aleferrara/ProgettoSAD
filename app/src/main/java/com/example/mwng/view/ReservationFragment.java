package com.example.mwng.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mwng.viewmodel.ReservationViewModel;
import com.example.mwng.databinding.FragmentReservationBinding;

public class ReservationFragment extends Fragment {

    private ReservationViewModel mViewModel;

    public static ReservationFragment newInstance() {
        return new ReservationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentReservationBinding binding = FragmentReservationBinding.inflate(inflater);
        mViewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
        binding.setReservationViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        binding.calendar.setMinDate(System.currentTimeMillis());

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);
        // TODO: Use the ViewModel
    }

}