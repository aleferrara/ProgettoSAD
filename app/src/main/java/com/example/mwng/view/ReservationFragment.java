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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReservationViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.matt1:
                mViewModel.onSelection(1);
                break;
            case R.id.matt2:
                mViewModel.onSelection(2);
                break;
            case R.id.pom1:
                mViewModel.onSelection(3);
                break;
            case R.id.pom2:
                mViewModel.onSelection(4);
                break;
        }
    }
}