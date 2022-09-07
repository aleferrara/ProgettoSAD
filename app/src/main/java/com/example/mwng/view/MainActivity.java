package com.example.mwng.view;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mwng.R;
import com.example.mwng.databinding.ActivityMainBinding;
import com.example.mwng.viewmodel.MainViewModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mViewModel;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding.setMainViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = binding.drawerLayout;
        navigationView = binding.nvView;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setupDrawerContent(navigationView);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainListFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_list:
                fragmentClass = UserListFragment.class;
                break;
            case R.id.nav_pren:
                fragmentClass = ReservationFragment.class;
                break;
            case R.id.nav_logout:
                mViewModel.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                fragmentClass = MainListFragment.class;
                break;
            default:
                fragmentClass = MainListFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

}