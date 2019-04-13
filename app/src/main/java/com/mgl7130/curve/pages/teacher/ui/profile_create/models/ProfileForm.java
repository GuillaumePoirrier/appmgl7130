package com.mgl7130.curve.pages.teacher.ui.profile_create.models;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;

public class ProfileForm extends BaseObservable {
    private ProfileFields fields = new ProfileFields();
    private ProfileErrorFields errors = new ProfileErrorFields();
    private MutableLiveData<ProfileFields> saveButton = new MutableLiveData();

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
        String description = this.fields.getTeacherDescription();
        if (description.length() < 30) {
            this.errors.setTeacherBirthDate((Integer)null);
            this.notifyPropertyChanged(11);
            return true;
        } else {
            if (setMessage) {
                this.errors.setTeacherDescription(2131624023);
                this.notifyPropertyChanged(11);
            }

            return false;
        }
    }

    public boolean isBirthDateValid(boolean setMessage) {
        String birthDate = this.fields.getTeacherBirthDate();
        if (birthDate.indexOf("/") == 2 && birthDate.indexOf("/") == 5 && birthDate.length() == 10) {
            this.errors.setTeacherBirthDate((Integer)null);
            this.notifyPropertyChanged(11);
            return true;
        } else {
            if (setMessage) {
                this.errors.setTeacherBirthDate(2131624020);
                this.notifyPropertyChanged(11);
            }
            return false;
        }
    }

    public boolean isFamilyNameValid(boolean setMessage) {
        String familyName = this.fields.getTeacherFamilyName();
        if (familyName != null) {
            this.errors.setTeacherFamilyName((Integer)null);
            this.notifyPropertyChanged(11);
            return true;
        } else {
            if (setMessage) {
                this.errors.setTeacherFamilyName(2131624024);
                this.notifyPropertyChanged(11);
            }
            return false;
        }
    }

    public boolean isFirstNameValid(boolean setMessage) {
        String firstName = this.fields.getTeacherFirstName();
        if (firstName != null) {
            this.errors.setTeacherFirstName((Integer)null);
            this.notifyPropertyChanged(11);
            return true;
        } else {
            if (setMessage) {
                this.errors.setTeacherFirstName(2131624024);
                this.notifyPropertyChanged(11);
            }
            return false;
        }
    }

    public void onClick() {
        if (this.isValid()) {
            this.saveButton.setValue(this.fields);
        }

    }

    public MutableLiveData<ProfileFields> getSignUpFields() {
        return this.saveButton;
    }

    public ProfileFields getFields() {
        return this.fields;
    }

    @Bindable
    public Integer getTeacherFirstNameError() {
        return this.errors.getTeacherFirstName();
    }

    @Bindable
    public Integer getTeacherFamilyNameError() {
        return this.errors.getTeacherFamilyName();
    }

    @Bindable
    public Integer getTeacherBirthDateError() {
        return this.errors.getTeacherBirthDate();
    }

    @Bindable
    public Integer getTeacherDescriptionError() {
        return this.errors.getTeacherDescription();
    }
}