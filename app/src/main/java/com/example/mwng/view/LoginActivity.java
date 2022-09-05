package com.example.mwng.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mwng.R;
import com.example.mwng.databinding.ActivityLoginBinding;
import com.example.mwng.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private AppCompatButton loginButton;
    private TextInputEditText emailText, passwordText;
    private TextView register, resetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setLoginViewModel(loginViewModel);
        binding.setLifecycleOwner(this);
        loginButton = binding.loginBtn;
        loginButton.setOnClickListener(this);
        emailText = binding.email;
        passwordText = binding.password;
        register = binding.rgBtn;
        register.setOnClickListener(this);
        resetPassword = binding.pswRst;
        resetPassword.setOnClickListener(this);

        loginViewModel.getLoggedIn().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rgBtn:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
            case R.id.loginBtn:
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                loginViewModel.signIn(email, password);
                break;
            case R.id.pswRst:
                startActivity(new Intent(this, ResetPasswordActivity.class));
                break;
        }
    }
}