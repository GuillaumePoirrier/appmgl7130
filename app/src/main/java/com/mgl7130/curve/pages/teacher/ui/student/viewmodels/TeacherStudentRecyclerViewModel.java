package com.mgl7130.curve.pages.teacher.ui.student.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mgl7130.curve.models.Cours;

import java.util.ArrayList;
import java.util.List;

public class TeacherStudentRecyclerViewModel extends ViewModel {

    public static final String TAG = "TeacherStudentVM";

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private CollectionReference mCollection = mDb.collection("classes");
    private MutableLiveData<List<Cours>> mClasses;

    public LiveData<List<Cours>> getClasses() {
        if (mClasses == null) {
            mClasses = new MutableLiveData<>();
            loadClasses();
        }
        return mClasses;
    }

    private void loadClasses() {
        mCollection.whereEqualTo("teacher_id", mAuth.getCurrentUser().getUid())
                .whereEqualTo("hasStudent", true)
                .orderBy("date", Query.Direction.ASCENDING).limit(50)
                .addSnapshotListener((queryDocumentSnapshots, e) -> mClasses.setValue(toClasses(queryDocumentSnapshots)));
    }

    private List<Cours> toClasses(QuerySnapshot snapshots) {
        List<Cours> cours = new ArrayList<>();
        if (snapshots.isEmpty()) return cours;
        for (DocumentSnapshot document : snapshots.getDocuments()) {
            cours.add(document.toObject(Cours.class).withId(document.getId()));
        }
        return cours;
    }

}
