package com.mgl7130.curve.pages.auth.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mgl7130.curve.models.User;
import com.mgl7130.curve.pages.auth.models.SignUpFields;
import com.mgl7130.curve.pages.auth.models.SignUpForm;

public class SignUpViewModel extends ViewModel {

    public MutableLiveData<Exception> userCreated = new MutableLiveData<>();
    private SignUpForm signUp;
    private View.OnFocusChangeListener onFocusFirstName;
    private View.OnFocusChangeListener onFocusLastName;
    private View.OnFocusChangeListener onFocusEmail;
    private View.OnFocusChangeListener onFocusPassword;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    public void init() {
        signUp = new SignUpForm();

        onFocusFirstName = (view, focused) -> {
            EditText et = (EditText) view;
            if (et.getText().length() > 0 && !focused) {
                signUp.isFirstNameValid(true);
            }
        };

        onFocusLastName = (view, focused) -> {
            EditText et = (EditText) view;
            if (et.getText().length() > 0 && !focused) {
                signUp.isLastNameValid(true);
            }
        };

        onFocusEmail = (view, focused) -> {
            EditText et = (EditText) view;
            if (et.getText().length() > 0 && !focused) {
                signUp.isEmailValid(true);
            }
        };

        onFocusPassword = (view, focused) -> {
            EditText et = (EditText) view;
            if (et.getText().length() > 0 && !focused) {
                signUp.isPasswordValid(true);
            }
        };

    }

    public SignUpForm getSignUp() {
        return signUp;
    }

    public View.OnFocusChangeListener getFirstNameOnFocusChangeListener() {
        return onFocusFirstName;
    }

    public View.OnFocusChangeListener getLastNameOnFocusChangeListener() {
        return onFocusLastName;
    }

    public View.OnFocusChangeListener getEmailOnFocusChangeListener() {
        return onFocusEmail;
    }

    public View.OnFocusChangeListener getPasswordOnFocusChangeListener() {
        return onFocusPassword;
    }

    public void onButtonClicked() {
        signUp.onClick();
    }

    public MutableLiveData<SignUpFields> getSignUpFields() {
        return signUp.getSignUpFields();
    }

    public void createUser(SignUpFields fields) {
        mAuth.createUserWithEmailAndPassword(fields.getEmail(), fields.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mDb.collection("users").document(mAuth.getCurrentUser().getUid())
                                .set(new User(fields.getFirstName(), fields.getLastName()), SetOptions.merge())
                                .addOnSuccessListener(aVoid -> userCreated.setValue(null))
                                .addOnFailureListener(e -> userCreated.setValue(e));
                    }
                });
    }

}
