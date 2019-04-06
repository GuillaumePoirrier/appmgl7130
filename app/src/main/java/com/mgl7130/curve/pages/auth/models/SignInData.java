package com.mgl7130.curve.pages.auth.models;

import android.arch.lifecycle.MutableLiveData;

import com.mgl7130.curve.pages.auth.ProfileChoiceActivity;

public class SignInData {

    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> rememberMe = new MutableLiveData<>();
    public MutableLiveData<Class<ProfileChoiceActivity>> startActivity = new MutableLiveData<>();

}
