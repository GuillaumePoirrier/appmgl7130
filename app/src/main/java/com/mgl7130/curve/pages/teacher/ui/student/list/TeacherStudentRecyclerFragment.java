package com.mgl7130.curve.pages.teacher.ui.student.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.mgl7130.curve.R;
import com.mgl7130.curve.pages.teacher.ui.student.detail.TeacherStudentDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherStudentRecyclerFragment extends Fragment implements
        TeacherStudentAdapter.OnClassSelectedListener {

    public static final String TAG = "StudentSearchRecyclerFragment";
    public static final int LIMIT = 50;

    @BindView(R.id.recycler_view)
    RecyclerView mClassRecycler;

    @BindView(R.id.viewEmpty)
    ViewGroup mEmptyView;

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private Query mQuery;

    private TeacherStudentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_student_recycler_view_fragment, container, false);
        ButterKnife.bind(this, view);

        //Firestore
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //Get ${LIMIT} class where teacherId == user id
        mQuery = mFirestore.collection("classes")
                .whereEqualTo("teacher_id", mAuth.getCurrentUser().getUid())
                .whereEqualTo("hasStudent", true)
                .orderBy("date", Query.Direction.ASCENDING)
                .limit(LIMIT);

        //RecyclerView
        mAdapter = new TeacherStudentAdapter(mQuery, this) {
            @Override
            protected void onDataChanged() {
                mClassRecycler.setVisibility(View.VISIBLE);
                mEmptyView.setVisibility(View.GONE);
            }

            @Override
            protected void onError(FirebaseFirestoreException e) {
                // Show a snackbar on errors
                Log.e(TAG,e.toString());
            }
        };

        mClassRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mClassRecycler.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Start listening for Firestore updates
        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    @Override
    public void onClassSelected(DocumentSnapshot cours) {
        // Go to the details page for the selected restaurant
        Intent intent = new Intent(getActivity(), TeacherStudentDetailActivity.class);
        intent.putExtra(TeacherStudentDetailActivity.KEY_CLASS_ID, cours.getId());

        startActivity(intent);
    }

    public static Fragment newInstance(){
        return new TeacherStudentRecyclerFragment();
    }

}
