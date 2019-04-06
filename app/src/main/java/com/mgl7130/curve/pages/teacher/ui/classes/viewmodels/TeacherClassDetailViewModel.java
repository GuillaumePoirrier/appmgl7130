package com.mgl7130.curve.pages.teacher.ui.classes.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mgl7130.curve.common.Resource;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Student;

import javax.annotation.Nullable;

public class TeacherClassDetailViewModel extends ViewModel {

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    private final MutableLiveData<String> mId = new MutableLiveData<>();
    private final LiveData<Cours> mCours;
    private final LiveData<Student> mStudent;

    private CollectionReference mClassesRef =mFirestore.collection("classes");
    private CollectionReference mStudentRef =mFirestore.collection("users");


    public TeacherClassDetailViewModel() {
        mCours = Transformations.switchMap(mId, this::getFirebaseCours);
        mStudent = Transformations.switchMap(mCours, this::getFirebaseStudent);
    }

    public TeacherClassDetailViewModel setClassId(final String id) {
        if (id == null) {
            return null;
        }
        this.mId.setValue(id);
        return this;
    }

    private LiveData<Cours> getFirebaseCours(final String id) {
        if (id == null) return null ;
        final DocumentReference classRef = mClassesRef.document(id);
        LiveData<Cours> data = new MutableLiveData<>();
        classRef.addSnapshotListener((snapshot, e) -> ((MutableLiveData<Cours>) data).setValue(snapshot.toObject(Cours.class)));
        return data;
    }

    private LiveData<Student> getFirebaseStudent(final Cours cours) {
        if (!cours.hasStudent) return null;
        final DocumentReference classRef = mStudentRef.document(cours.getStudent_id());
        LiveData<Student> data = new MutableLiveData<>();
        classRef.addSnapshotListener((snapshot, e) -> ((MutableLiveData<Student>) data).setValue(snapshot.toObject(Student.class)));
        return data;
    }

    public LiveData<Cours> getCours() {
        return mCours;
    }

    public LiveData<Student> getStudent() {
        return mStudent;
    }

}

