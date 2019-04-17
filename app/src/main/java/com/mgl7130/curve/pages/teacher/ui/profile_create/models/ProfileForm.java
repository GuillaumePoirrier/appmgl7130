package com.mgl7130.curve.pages.teacher.ui.profile_create.models;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.mgl7130.curve.R;

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
            this.errors.setTeacherBirthDate(null);
            this.notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                this.errors.setTeacherDescription(R.string.error_too_short);
                this.notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public boolean isBirthDateValid(boolean setMessage) {
        String birthDate = this.fields.getTeacherBirthDate();
        if (birthDate.indexOf("/") == 2 && birthDate.indexOf("/") == 5 && birthDate.length() == 10) {
            this.errors.setTeacherBirthDate(null);
            this.notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                this.errors.setTeacherBirthDate(R.string.error_date_format);
                this.notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public boolean isFamilyNameValid(boolean setMessage) {
        String familyName = this.fields.getTeacherFamilyName();
        if (familyName != null) {
            this.errors.setTeacherFamilyName(null);
            this.notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                this.errors.setTeacherFamilyName(R.string.error_must_fill);
                this.notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public boolean isFirstNameValid(boolean setMessage) {
        String firstName = this.fields.getTeacherFirstName();
        if (firstName != null) {
            this.errors.setTeacherFirstName(null);
            this.notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                this.errors.setTeacherFirstName(R.string.error_must_fill);
                this.notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public void onClick() {
        if (this.isValid()) {
            this.saveButton.setValue(this.fields);
        }
    }

    public MutableLiveData<ProfileFields> getProfileFields() {
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