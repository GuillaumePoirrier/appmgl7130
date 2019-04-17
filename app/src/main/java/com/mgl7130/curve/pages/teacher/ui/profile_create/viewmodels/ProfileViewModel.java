package com.mgl7130.curve.pages.teacher.ui.profile_create.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mgl7130.curve.pages.teacher.ui.profile_create.models.ProfileFields;
import com.mgl7130.curve.pages.teacher.ui.profile_create.models.ProfileForm;

import java.io.ByteArrayOutputStream;

public class ProfileViewModel extends ViewModel {
    private ProfileForm profile;
    private View.OnFocusChangeListener onFocusTeacherFirstName;
    private View.OnFocusChangeListener onFocusTeacherFamilyName;
    private View.OnFocusChangeListener onFocusTeacherBirthDate;
    private View.OnFocusChangeListener onFocusTeacherDescription;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();

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

    public ProfileForm getProfile() { return this.profile; }

    public OnFocusChangeListener getTeacherFirstNameOnFocusChangeListener() { return this.onFocusTeacherFirstName; }

    public OnFocusChangeListener getTeacherFamilyNameOnFocusChangeListener() { return this.onFocusTeacherFamilyName; }

    public OnFocusChangeListener getTeacherDescriptionOnFocusChangeListener() { return this.onFocusTeacherDescription; }

    public OnFocusChangeListener getTeacherBirthDateOnFocusChangeListener() { return this.onFocusTeacherBirthDate; }

    public void onButtonSaveClicked() {
        this.profile.onClick();
    }

    public MutableLiveData<ProfileFields> getProfileFields() { return this.profile.getProfileFields(); }


    // Traitement d'image

    public Bitmap cropPicture(Bitmap picture) {
        Bitmap dstBmp;
        if (picture.getWidth() >= picture.getHeight()) {
            dstBmp = Bitmap.createBitmap(
                    picture,
                    picture.getWidth() / 2 - picture.getHeight() / 2,
                    0,
                    picture.getHeight(),
                    picture.getHeight()
            );
        } else {
            dstBmp = Bitmap.createBitmap(
                    picture,
                    0,
                    picture.getHeight() / 2 - picture.getWidth() / 2,
                    picture.getWidth(),
                    picture.getWidth()
            );
        }
        saveCroppedPictureInFirebase(dstBmp);
        return dstBmp;
    }

    private void saveCroppedPictureInFirebase(Bitmap picture) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("curve/" + mAuth.getInstance().getCurrentUser().getUid() + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // TODO
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // TODO
            }
        });
    }

}