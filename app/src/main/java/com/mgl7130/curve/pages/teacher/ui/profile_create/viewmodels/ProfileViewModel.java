package com.mgl7130.curve.pages.teacher.ui.profile_create.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mgl7130.curve.pages.teacher.ui.profile_create.models.ProfileForm;

public class ProfileViewModel extends ViewModel {
    private ProfileForm profile;
    private OnFocusChangeListener onFocusTeacherFirstName;
    private OnFocusChangeListener onFocusTeacherFamilyName;
    private OnFocusChangeListener onFocusTeacherBirthDate;
    private OnFocusChangeListener onFocusTeacherDescription;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    public ProfileViewModel() {
    }

    public void init() {
        this.profile = new ProfileForm();
        this.onFocusTeacherFirstName = (view, focused) -> {
            EditText et = (EditText)view;
            if (et.getText().length() > 0 && !focused) {
                this.profile.isFirstNameValid(true);
            }

        };
        this.onFocusTeacherFamilyName = (view, focused) -> {
            EditText et = (EditText)view;
            if (et.getText().length() > 0 && !focused) {
                this.profile.isFamilyNameValid(true);
            }

        };
        this.onFocusTeacherDescription = (view, focused) -> {
            EditText et = (EditText)view;
            if (et.getText().length() > 0 && !focused) {
                this.profile.isDescriptionValid(true);
            }

        };
        this.onFocusTeacherBirthDate = (view, focused) -> {
            EditText et = (EditText)view;
            if (et.getText().length() > 0 && !focused) {
                this.profile.isBirthDateValid(true);
            }

        };
    }

    public ProfileForm getProfile() {
        return this.profile;
    }

    public OnFocusChangeListener getTeacherFirstNameOnFocusChangeListener() {
        return this.onFocusTeacherFirstName;
    }

    public OnFocusChangeListener getTeacherFamilyNameOnFocusChangeListener() {
        return this.onFocusTeacherFamilyName;
    }

    public OnFocusChangeListener getTeacherDescriptionOnFocusChangeListener() {
        return this.onFocusTeacherDescription;
    }

    public OnFocusChangeListener getTeacherBirthDateOnFocusChangeListener() {
        return this.onFocusTeacherBirthDate;
    }

    public void onButtonSaveClicked() {
        this.profile.onClick();
    }
/*
    public MutableLiveData<ProfileFields> getProfileFields() {
        return this.profile.getProfileFields();
    }
*/
}