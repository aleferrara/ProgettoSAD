package com.example.mwng.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mwng.R;
import com.example.mwng.model.Cat;
import com.example.mwng.viewmodel.CatProfileViewModel;
import com.example.mwng.databinding.FragmentCatProfileBinding;

import java.util.ArrayList;

public class CatProfileFragment extends Fragment implements View.OnClickListener {

    private CatProfileViewModel mViewModel;
    private ImageButton addButton, removeButton;
    private TextView textNome, textEta, textSesso, textRazza;
    String chiave;
    private ArrayList<Cat> catArrayList;
    private FragmentCatProfileBinding binding;


    public static CatProfileFragment newInstance() {
        return new CatProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCatProfileBinding.inflate(inflater);
        mViewModel = ViewModelProviders.of(this).get(CatProfileViewModel.class);
        binding.setCatProfileViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        addButton = binding.addBtn;
        addButton.setOnClickListener(this);
        removeButton = binding.removeBtn;
        removeButton.setOnClickListener(this);
        Bundle data = getArguments();
        textNome = binding.textNome;
        textEta = binding.textEta;
        textSesso = binding.textSesso;
        textRazza = binding.textRazza;
        String name = data.getString("nome");
        String eta = data.getString("eta");
        String sesso = data.getString("sesso");
        String razza = data.getString("razza");
        String image = data.getString("image");
        chiave = data.getString("chiave");
        Glide.with(this).load(image).into(binding.catProfileImage);
        textNome.setText(name);
        textEta.setText(eta);
        textSesso.setText(sesso);
        textRazza.setText(razza);

        mViewModel.getCatArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Cat>>() {
            @Override
            public void onChanged(ArrayList<Cat> cats) {
                catArrayList = mViewModel.getCatArrayList().getValue();
                if (mViewModel.isInUserList(catArrayList, chiave)) {
                    addButton.setClickable(false);
                    addButton.setImageDrawable(getActivity().getDrawable(R.drawable.ic_add_grey));
                    removeButton.setClickable(true);
                    removeButton.setImageDrawable(getActivity().getDrawable(R.drawable.ic_remove_red));
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CatProfileViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {

    }
}