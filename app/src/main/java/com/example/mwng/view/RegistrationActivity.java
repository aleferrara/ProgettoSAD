package com.example.mwng.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mwng.R;

import com.example.mwng.databinding.ActivityRegistrationBinding;
import com.example.mwng.viewmodel.RegistrationViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityRegistrationBinding binding;
    private RegistrationViewModel mViewModel;
    private TextInputEditText textNome;
    private TextInputEditText textCognome;
    private TextInputEditText textEmail;
    private TextInputEditText textPsw1;
    private TextInputEditText textPsw2;
    private AppCompatButton registrationButton;
    private TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        mViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        binding.setRegistrationViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        textNome = binding.nome;
        textCognome = binding.cognome;
        textEmail = binding.email;
        textPsw1 = binding.password;
        textPsw2 = binding.password2;
        registrationButton = binding.registerBtn;
        registrationButton.setOnClickListener(this);
        loginButton = binding.lgBtn;
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.registerBtn:
                String nome = textNome.getText().toString().trim();
                String cognome = textCognome.getText().toString().trim();
                String email = textEmail.getText().toString().trim();
                String password = textPsw1.getText().toString();
                mViewModel.signUp(nome, cognome, email, password);
                mViewModel.getUserCreated().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(getApplicationContext(), "Utente creato correttamente", Toast.LENGTH_LONG).show();
                        } else {
                            mViewModel.getErrorMessage().observe(binding.getLifecycleOwner(), new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.lgBtn:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }

    }

}