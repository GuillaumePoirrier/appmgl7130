package com.mgl7130.curve.pages.auth.models;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.mgl7130.curve.BR;
import com.mgl7130.curve.R;

public class SignUpForm extends BaseObservable {

    private SignUpFields fields = new SignUpFields();
    private SignUpErrorFields errors = new SignUpErrorFields();
    private MutableLiveData<SignUpFields> buttonClick = new MutableLiveData<>();

    @Bindable
    public boolean isValid() {
        boolean valid = isEmailValid(false);
        valid = isPasswordValid(false) && isFirstNameValid(false) && isLastNameValid(false) && valid;
        notifyPropertyChanged(BR.emailError);
        notifyPropertyChanged(BR.passwordError);
        notifyPropertyChanged(BR.firstNameError);
        notifyPropertyChanged(BR.lastNameError);
        return valid;
    }

    public boolean isEmailValid(boolean setMessage) {
        // Minimum a@b.c
        String email = fields.getEmail();
        if (email != null && email.length() > 5) {
            int indexOfAt = email.indexOf("@");
            int indexOfDot = email.lastIndexOf(".");
            if (indexOfAt > 0 && indexOfDot > indexOfAt && indexOfDot < email.length() - 1) {
                errors.setEmail(null);
                notifyPropertyChanged(BR.valid);
                return true;
            } else {
                if (setMessage) {
                    errors.setEmail(R.string.error_format_invalid);
                    notifyPropertyChanged(BR.valid);
                }
                return false;
            }
        }
        if (setMessage) {
            errors.setEmail(R.string.error_too_short);
            notifyPropertyChanged(BR.valid);
        }
        return false;
    }

    public boolean isPasswordValid(boolean setMessage) {
        String password = fields.getPassword();
        if (password != null && password.length() > 6) {
            errors.setPassword(null);
            notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                errors.setPassword(R.string.error_too_short);
                notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public boolean isFirstNameValid(boolean setMessage) {
        String firstName = fields.getFirstName();
        if (firstName != null) {
            errors.setFirstName(null);
            notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                errors.setFirstName(R.string.error_must_fill);
                notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public boolean isLastNameValid(boolean setMessage) {
        String lastName = fields.getLastName();
        if (lastName != null) {
            errors.setLastName(null);
            notifyPropertyChanged(BR.valid);
            return true;
        } else {
            if (setMessage) {
                errors.setLastName(R.string.error_must_fill);
                notifyPropertyChanged(BR.valid);
            }
            return false;
        }
    }

    public void onClick() {
        if (isValid()) buttonClick.setValue(fields);
    }

    public MutableLiveData<SignUpFields> getSignUpFields() {
        return buttonClick;
    }

    public SignUpFields getFields() {
        return fields;
    }

    @Bindable
    public Integer getEmailError() {
        return errors.getEmail();
    }

    @Bindable
    public Integer getPasswordError() {
        return errors.getPassword();
    }

    @Bindable
    public Integer getFirstNameError() {
        return errors.getFirstName();
    }

    @Bindable
    public Integer getLastNameError() {
        return errors.getLastName();
    }

}
