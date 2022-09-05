package com.example.mwng.view;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
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

import com.example.mwng.model.Cat;
import com.example.mwng.viewmodel.MainListViewModel;
import com.example.mwng.databinding.FragmentMainListBinding;
import com.example.mwng.R;

import java.util.ArrayList;

public class MainListFragment extends Fragment {

    private MainListViewModel mViewModel;
    private MutableLiveData<ArrayList<Cat>> arrayListMutableLiveData;
    private ArrayList<Cat> catArrayList;

    public static MainListFragment newInstance() {
        return new MainListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentMainListBinding binding = FragmentMainListBinding.inflate(inflater);
        mViewModel = ViewModelProviders.of(this).get(MainListViewModel.class);
        binding.setMainListViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        mViewModel.getCatArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Cat>>() {
            @Override
            public void onChanged(ArrayList<Cat> cats) {
                catArrayList = mViewModel.getCatArrayList().getValue();
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
        mViewModel = new ViewModelProvider(this).get(MainListViewModel.class);
        // TODO: Use the ViewModel
    }

}