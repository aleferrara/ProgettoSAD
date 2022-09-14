package com.example.mwng.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mwng.R;

import com.example.mwng.databinding.ActivityRegistrationBinding;
import com.example.mwng.viewmodel.RegistrationViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
    private TextInputLayout tilEmail;
    private TextInputLayout tilPsw;
    private TextInputLayout tilPsw2;
    private TextInputLayout tilNome;
    private TextInputLayout tilCognome;
    private ProgressBar progressBar;

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
        tilNome = binding.tilNome;
        tilCognome = binding.tilCognome;
        tilEmail = binding.tilEmail;
        tilPsw = binding.tilPsw;
        tilPsw2 = binding.tilPsw2;
        progressBar = binding.progressBar;

        textNome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tilNome.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textCognome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tilCognome.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tilEmail.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textPsw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tilPsw.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textPsw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tilPsw2.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.registerBtn:
                String nome = textNome.getText().toString().trim();
                String cognome = textCognome.getText().toString().trim();
                String email = textEmail.getText().toString().trim();
                String password = textPsw1.getText().toString();
                String password2 = textPsw2.getText().toString();

                if (nome.isEmpty()){
                    tilNome.setError("Inserire nome");
                    tilNome.requestFocus();
                    return;
                }

                if (cognome.isEmpty()){
                    tilCognome.setError("Inserire cognome");
                    tilCognome.requestFocus();
                    return;
                }

                if (email.isEmpty()){
                    tilEmail.setError("Inserire email");
                    tilEmail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    tilEmail.setError("Inserire email valida");
                    tilEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()){
                    tilPsw.setError("Inserire password");
                    tilPsw.requestFocus();
                    return;
                }

                if (password.length() < 6){
                    tilPsw.setError("La password deve contenere almeno 6 caratteri");
                    tilPsw.requestFocus();
                    return;
                }

                if (password2.isEmpty()){
                    tilPsw.setError("Inserire password");
                    tilPsw.requestFocus();
                    return;
                }

                if (password2.length() < 6){
                    tilPsw2.setError("La password deve contenere almeno 6 caratteri");
                    tilPsw2.requestFocus();
                    return;
                }

                if (!(password.equals(password2))){
                    tilPsw.setError("Le password non corrispondono");
                    tilPsw.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mViewModel.signUp(nome, cognome, email, password);
                mViewModel.getUserCreated().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(getApplicationContext(), "Utente creato correttamente", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(RegistrationActivity.this, PostRegistrationActivity.class));
                        } else {
                            progressBar.setVisibility(View.GONE);
                            mViewModel.getErrorMessage().observe(binding.getLifecycleOwner(), new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    if (!s.equals("null"))
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