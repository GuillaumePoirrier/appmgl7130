package com.mgl7130.curve.pages.teacher.ui.classes.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.pages.teacher.ui.classes.views.TeacherClassFormActivity;

import java.util.ArrayList;
import java.util.List;

public class TeacherClassRecyclerViewModel extends ViewModel {

    public static final String TAG = "TeacherClassListVM";

    public MutableLiveData<Class<TeacherClassFormActivity>> addClassActivity = new MutableLiveData<>();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mDb = FirebaseFirestore.getInstance();
    private CollectionReference mCollection = mDb.collection("classes");
    private MutableLiveData<List<Cours>> classes;

    public LiveData<List<Cours>> getClasses() {
        if (classes == null) {
            classes = new MutableLiveData<>();
            loadClasses();
        }
        return classes;
    }

    private void loadClasses() {
        mCollection.whereEqualTo("teacher_id", mAuth.getCurrentUser().getUid())
                .orderBy("date", Query.Direction.ASCENDING)
                .limit(20)
                .addSnapshotListener((queryDocumentSnapshots, e) -> classes.setValue(toClasses(queryDocumentSnapshots)));
    }

    private List<Cours> toClasses(QuerySnapshot snapshots) {
        List<Cours> cours = new ArrayList<>();
        if (snapshots.isEmpty()) return cours;
        for (DocumentSnapshot document : snapshots.getDocuments()) {
            cours.add(document.toObject(Cours.class).withId(document.getId()));
        }
        return cours;
    }


    public void onAddClassFABClicked() {
        Log.d(TAG, "fab PRESSED");
        addClassActivity.setValue(TeacherClassFormActivity.class);
    }

}
