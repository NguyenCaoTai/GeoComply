package com.sfg.geocomply.ui.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sfg.geocomply.domain.GetPageTitleFromUrl;
import com.sfg.geocomply.domain.GetPageTitleFromUrlImpl;
import com.sfg.geocomply.data.NetService;
import com.sfg.geocomply.data.NetServiceImpl;
import com.sfg.geocomply.R;
import com.sfg.geocomply.databinding.MainFragmentBinding;

public class MainFragment extends Fragment {

    private MainFragmentBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainViewModel mViewModel = new ViewModelProvider(this, getMainViewModelFactory())
                .get(MainViewModel.class);

        binding.setViewModel(mViewModel);
    }

    private MainViewModel.MainViewModelFactory getMainViewModelFactory(){
        NetService netService = new NetServiceImpl();
        GetPageTitleFromUrl getPageTitleFromUrl = new GetPageTitleFromUrlImpl(netService);

        return new MainViewModel.MainViewModelFactory(getPageTitleFromUrl);
    }

}