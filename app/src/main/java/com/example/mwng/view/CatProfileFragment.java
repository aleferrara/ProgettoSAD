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
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mwng.R;
import com.example.mwng.model.Cat;
import com.example.mwng.viewmodel.CatProfileViewModel;
import com.example.mwng.databinding.FragmentCatProfileBinding;

import java.util.ArrayList;

public class CatProfileFragment extends Fragment implements View.OnClickListener {

    private String nome;
    private String eta;
    private String sesso;
    private String razza;
    private String chiave;
    private String image;
    private CatProfileViewModel mViewModel;
    private ImageButton addButton, removeButton;
    private TextView textNome, textEta, textSesso, textRazza;
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
        nome = data.getString("nome");
        eta = data.getString("eta");
        sesso = data.getString("sesso");
        razza = data.getString("razza");
        image = data.getString("image");
        chiave = data.getString("chiave");
        Glide.with(this).load(image).into(binding.catProfileImage);
        textNome.setText(nome);
        textEta.setText(eta);
        textSesso.setText(sesso);
        textRazza.setText(razza);

        mViewModel.getCatArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Cat>>() {
            @Override
            public void onChanged(ArrayList<Cat> cats) {
                catArrayList = mViewModel.getCatArrayList().getValue();
                if (mViewModel.isInUserList(catArrayList, chiave)) {
                    addButton.setClickable(false);
                    addButton.setVisibility(View.GONE);
                    removeButton.setClickable(true);
                    removeButton.setVisibility(View.VISIBLE);
                } else {
                    addButton.setVisibility(View.VISIBLE);
                    addButton.setClickable(true);
                    removeButton.setVisibility(View.GONE);
                    removeButton.setClickable(false);
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
        switch (view.getId()) {
            case R.id.addBtn:
                new AlertDialog.Builder(getContext())
                        .setTitle("Aggiunto alla tua lista!").setIcon(R.drawable.ic_done)
                        .setMessage("Puoi visualizzare la tua lista nell'apposita sezione.")
                        .setPositiveButton("Okay!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
                mViewModel.addCat(chiave);
                addButton.setClickable(false);
                addButton.setVisibility(View.GONE);
                removeButton.setClickable(true);
                removeButton.setVisibility(View.VISIBLE);
                break;
            case R.id.removeBtn:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Rimuovere dalla lista personale?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Sì",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mViewModel.removeCat(chiave);
                                addButton.setVisibility(View.VISIBLE);
                                addButton.setClickable(true);
                                removeButton.setVisibility(View.GONE);
                                removeButton.setClickable(false);
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Annulla",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                break;
        }
    }
}