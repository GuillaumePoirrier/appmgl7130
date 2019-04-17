package com.mgl7130.curve.pages.teacher.ui.student.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Student;

public class TeacherStudentDetailViewModel extends ViewModel {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();

    private final MutableLiveData<String> mId = new MutableLiveData<>();
    private final LiveData<Cours> mCours;
    private final LiveData<Student> mStudent;

    private CollectionReference mClassesRef = mDb.collection("classes");
    private CollectionReference mStudentRef = mDb.collection("users");

    public TeacherStudentDetailViewModel() {
        mCours = Transformations.switchMap(mId, this::getFirebaseCours);
        mStudent = Transformations.switchMap(mCours, this::getFirebaseStudent);
    }

    public TeacherStudentDetailViewModel setId(final String id) {
        if (id == null) return null;
        this.mId.setValue(id);
        return this;
    }

    private LiveData<Cours> getFirebaseCours(final String id) {
        if (id == null) return null;
        final DocumentReference classRef = mClassesRef.document(id);
        LiveData<Cours> data = new MutableLiveData<>();
        classRef.addSnapshotListener((snapshot, e) -> ((MutableLiveData<Cours>) data).setValue(snapshot.toObject(Cours.class)));
        return data;
    }

    private LiveData<Student> getFirebaseStudent(final Cours cours) {
        if (!cours.hasStudent) return null;
        final DocumentReference classRef = mStudentRef.document(cours.getStudent_id());
        LiveData<Student> data = new MutableLiveData<>();
        classRef.addSnapshotListener((snapshot, e) -> ((MutableLiveData<Student>) data).setValue(snapshot.toObject(Student.class).withId(snapshot.getId())));
        return data;
    }

    public LiveData<Student> getStudent() {
        return mStudent;
    }


}
