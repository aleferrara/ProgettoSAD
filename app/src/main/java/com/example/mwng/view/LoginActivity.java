package com.example.mwng.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mwng.R;
import com.example.mwng.databinding.ActivityLoginBinding;
import com.example.mwng.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginViewModel mViewModel;
    private ActivityLoginBinding binding;
    private AppCompatButton loginButton;
    private TextInputEditText emailText, passwordText;
    private TextView register, resetPassword;
    private String email;
    private String password;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setLoginViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        loginButton = binding.loginBtn;
        loginButton.setOnClickListener(this);
        emailText = binding.email;
        passwordText = binding.password;
        register = binding.rgBtn;
        register.setOnClickListener(this);
        resetPassword = binding.pswRst;
        resetPassword.setOnClickListener(this);
        tilEmail = binding.tilEmail;
        tilPassword = binding.tilPsw;
        progressBar = binding.progressBar;

        mViewModel.getLoggedIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    progressBar.setVisibility(View.GONE);
                    mViewModel.getErrorMessage().observe(binding.getLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tilEmail.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                tilPassword.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rgBtn:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
            case R.id.loginBtn:
                email = emailText.getText().toString().trim();
                password = passwordText.getText().toString().trim();
                if (email.isEmpty()) {
                    tilEmail.setError("Inserire email");
                    tilEmail.requestFocus();
                    break;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    tilEmail.setError("Inserire email valida");
                    tilEmail.requestFocus();
                    break;
                }

                if (password.isEmpty()) {
                    tilPassword.setError("Inserire password");
                    tilPassword.requestFocus();
                    break;
                }

                if (password.length() < 6) {
                    tilPassword.setError("La password deve contenere almeno 6 caratteri");
                    tilPassword.requestFocus();
                    break;
                }
                progressBar.setVisibility(View.VISIBLE);
                mViewModel.signIn(email, password);
                break;
            case R.id.pswRst:
                startActivity(new Intent(this, ResetPasswordActivity.class));
                break;
        }
    }
}