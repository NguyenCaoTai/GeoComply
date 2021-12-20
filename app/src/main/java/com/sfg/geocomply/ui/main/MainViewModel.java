package com.sfg.geocomply.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public final MutableLiveData<String> comment = new MutableLiveData<>("");
    public final LiveData<String> mentionResponse = new MutableLiveData<>("");
    public final LiveData<String> linkResponse = new MutableLiveData<>("");
}