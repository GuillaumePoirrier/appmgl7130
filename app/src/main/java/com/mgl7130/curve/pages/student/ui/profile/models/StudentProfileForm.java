package com.mgl7130.curve.pages.student.ui.profile.models;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.mgl7130.curve.R;


public class StudentProfileForm extends BaseObservable {

    private StudentProfileFields fields = new StudentProfileFields();
    private StudentProfileErrorFields errors = new StudentProfileErrorFields();
    private MutableLiveData<StudentProfileFields> saveButtonStudent = new MutableLiveData();


    @Bindable
    public boolean isValid() {
        boolean valid = this.isFirstNameValid(false);
        valid = this.isFamilyNameValid(false) && this.isBirthDateValid(false) && this.isDescriptionValid(false) && valid;
        this.notifyPropertyChanged(BR.teacherFirstNameError);
        this.notifyPropertyChanged(BR.teacherDescriptionError);
        this.notifyPropertyChanged(BR.teacherFamilyNameError);
        this.notifyPropertyChanged(BR.teacherBirthDateError);
        return valid;
    }

    public boolean isDescriptionValid(boolean setMessage) {
        String description = this.fields.getStudentDescription();
        if (description.length() < 30) {
            this.errors.setStudentBirthDate(null);
            this.notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                this.errors.setStudentDescription(R.string.error_too_short);
                this.notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public boolean isBirthDateValid(boolean setMessage) {
        String birthDate = this.fields.getStudentBirthDate();
        if (birthDate.indexOf("/") == 2 && birthDate.indexOf("/") == 5 && birthDate.length() == 10) {
            this.errors.setStudentBirthDate(null);
            this.notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                this.errors.setStudentBirthDate(R.string.error_date_format);
                this.notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public boolean isFamilyNameValid(boolean setMessage) {
        String familyName = this.fields.getStudentFamilyName();
        if (familyName != null) {
            this.errors.setStudentFamilyName(null);
            this.notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                this.errors.setStudentFamilyName(R.string.error_must_fill);
                this.notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public boolean isFirstNameValid(boolean setMessage) {
        String firstName = this.fields.getStudentFirstName();
        if (firstName != null) {
            this.errors.setStudentFirstName(null);
            this.notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                this.errors.setStudentFirstName(R.string.error_must_fill);
                this.notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public void onClick() {
        if (this.isValid()) {
            this.saveButtonStudent.setValue(this.fields);
        }
    }

    public MutableLiveData<StudentProfileFields> getSignUpFields() { return this.saveButtonStudent; }

    public StudentProfileFields getFields() {
        return this.fields;
    }

    @Bindable
    public Integer getStudentFirstNameError() {
        return this.errors.getStudentFirstName();
    }

    @Bindable
    public Integer getStudentFamilyNameError() {
        return this.errors.getStudentFamilyName();
    }

    @Bindable
    public Integer getStudentBirthDateError() {
        return this.errors.getStudentBirthDate();
    }

    @Bindable
    public Integer getStudentDescriptionError() {
        return this.errors.getStudentDescription();
    }

}
