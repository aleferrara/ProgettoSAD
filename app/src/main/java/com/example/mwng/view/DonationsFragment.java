package com.example.mwng.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mwng.R;
import com.example.mwng.databinding.FragmentDonationsBinding;

public class DonationsFragment extends Fragment implements View.OnClickListener {

    private FragmentDonationsBinding binding;
    private ImageButton PayPalButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentDonationsBinding.inflate(inflater);
        PayPalButton = binding.PaypalButton;
        PayPalButton.setOnClickListener(this);

        return binding.getRoot();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.PaypalButton:
                String url = "http://www.paypal.com/paypalme/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;
        }
    }
}