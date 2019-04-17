package com.mgl7130.curve.pages.student.ui.profile.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mgl7130.curve.pages.student.ui.profile.models.StudentProfileForm;

public class StudentProfileViewModel extends ViewModel {

    private StudentProfileForm studentProfile;
    private View.OnFocusChangeListener onFocusStudentFirstName;
    private View.OnFocusChangeListener onFocusStudentFamilyName;
    private View.OnFocusChangeListener onFocusStudentBirthDate;
    private View.OnFocusChangeListener onFocusStudentDescription;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();


    public void init() {
        this.studentProfile = new StudentProfileForm();
        this.onFocusStudentFirstName = (view, focused) -> {
            EditText et = (EditText)view;
            if (et.getText().length() > 0 && !focused) {
                this.studentProfile.isFirstNameValid(true);
            }

        };
        this.onFocusStudentFamilyName = (view, focused) -> {
            EditText et = (EditText)view;
            if (et.getText().length() > 0 && !focused) {
                this.studentProfile.isFamilyNameValid(true);
            }

        };
        this.onFocusStudentDescription = (view, focused) -> {
            EditText et = (EditText)view;
            if (et.getText().length() > 0 && !focused) {
                this.studentProfile.isDescriptionValid(true);
            }

        };
        this.onFocusStudentBirthDate = (view, focused) -> {
            EditText et = (EditText)view;
            if (et.getText().length() > 0 && !focused) {
                this.studentProfile.isBirthDateValid(true);
            }

        };
    }

    public StudentProfileForm getStudentProfile() {
        return this.studentProfile;
    }

    public OnFocusChangeListener getStudentFirstNameOnFocusChangeListener() { return this.onFocusStudentFirstName; }

    public OnFocusChangeListener getStudentFamilyNameOnFocusChangeListener() { return this.onFocusStudentFamilyName; }

    public OnFocusChangeListener getStudentDescriptionOnFocusChangeListener() { return this.onFocusStudentDescription; }

    public OnFocusChangeListener getStudentBirthDateOnFocusChangeListener() { return this.onFocusStudentBirthDate; }


    public void onButtonSaveClickedStudent() {
        this.studentProfile.onClick();
    }

/*
    public MutableLiveData<ProfileFields> getProfileFields() {
        return this.profile.getProfileFields();
    }
*/


}
