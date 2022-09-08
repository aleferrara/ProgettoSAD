package com.example.mwng.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mwng.R;
import com.example.mwng.databinding.ActivityResetPasswordBinding;
import com.example.mwng.viewmodel.ResetPasswordViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityResetPasswordBinding binding;
    private ResetPasswordViewModel mViewModel;
    private AppCompatButton resetPsw;
    private TextInputEditText emailText;
    private TextInputLayout emailTil;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        mViewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel.class);
        binding.setResetPasswordViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        resetPsw = binding.pswRst;
        resetPsw.setOnClickListener(this);
        emailText = binding.emailRst;
        emailTil = binding.tilEmail;
        progressBar = binding.progressBar;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pswRst:
                String email = emailText.getText().toString().trim();
                if (email.isEmpty()) {
                    emailTil.setError("Inserire email");
                    emailTil.requestFocus();
                    break;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailTil.setError("Inserire email valida");
                    emailTil.requestFocus();
                    break;
                }
                progressBar.setVisibility(View.VISIBLE);
                mViewModel.resetPassword(email);
                mViewModel.getEmailSent().observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        progressBar.setVisibility(View.GONE);
                        if (aBoolean) {
                            Toast.makeText(ResetPasswordActivity.this, "Controlla la tua email per resettare la password!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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

                progressBar.setVisibility(View.GONE);

                break;
        }
    }
}
