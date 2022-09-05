package com.example.mwng.view;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import android.widget.AdapterView;

import com.example.mwng.R;
import com.example.mwng.databinding.FragmentMainListBinding;
import com.example.mwng.databinding.FragmentUserListBinding;
import com.example.mwng.model.Cat;
import com.example.mwng.viewmodel.MainListViewModel;
import com.example.mwng.viewmodel.UserListViewModel;

import java.util.ArrayList;

public class UserListFragment extends Fragment {

    private UserListViewModel mViewModel;
    private FragmentUserListBinding binding;
    private ArrayList<Cat> catArrayList;

    public static UserListFragment newInstance() {
        return new UserListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentUserListBinding.inflate(inflater);
        mViewModel = ViewModelProviders.of(this).get(UserListViewModel.class);
        binding.setUserListViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        mViewModel.getCatArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Cat>>() {
            @Override
            public void onChanged(ArrayList<Cat> cats) {
                catArrayList = mViewModel.getCatArrayList().getValue();
                Log.i("LISTA SIZE", String.valueOf(catArrayList.size()));
                ListAdapter listAdapter = new ListAdapter(getContext(), catArrayList);
                binding.listViewID.setAdapter(listAdapter);
                binding.listViewID.deferNotifyDataSetChanged();
                binding.listViewID.setClickable(true);
                binding.listViewID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Bundle data = mViewModel.onListItemClicked(adapterView, view, i);
                        CatProfileFragment catProfileFragment = new CatProfileFragment();
                        catProfileFragment.setArguments(data);
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, catProfileFragment);
                        fragmentTransaction.commit();
                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserListViewModel.class);
        // TODO: Use the ViewModel
    }

}