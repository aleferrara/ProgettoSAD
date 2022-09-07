package com.example.mwng.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mwng.R;
import com.example.mwng.databinding.ActivityPostRegistrationBinding;
import com.example.mwng.viewmodel.PostRegistrationViewModel;

public class PostRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    PostRegistrationViewModel mViewModel;
    ActivityPostRegistrationBinding binding;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_registration);
        mViewModel = ViewModelProviders.of(this).get(PostRegistrationViewModel.class);

        loginButton = binding.postRegLoginButton;
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.postRegLoginButton:
                Log.i("button", "premuto");
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }

    }
}