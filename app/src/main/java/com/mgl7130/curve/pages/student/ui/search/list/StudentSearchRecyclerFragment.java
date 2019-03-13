package com.mgl7130.curve.pages.student.ui.search.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.pages.student.ui.search.detail.StudentSearchDetailActivity;
import com.mgl7130.curve.pages.student.ui.search.dialog.FilterDialogFragment;
import com.mgl7130.curve.pages.student.ui.search.model.Filters;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentSearchRecyclerFragment extends Fragment implements
        StudentSearchAdapter.OnClassSelectedListener {

    public static final String TAG = "StudentSearchRecyclerFragment";
    public static final int LIMIT = 50;

    @BindView(R.id.textCurrentSearch)
    TextView mCurrentSearchView;

    @BindView(R.id.textCurrentSortBy)
    TextView mCurrentSortByView;

    @BindView(R.id.recycler_view)
    RecyclerView mClassRecycler;

    @BindView(R.id.viewEmpty)
    ViewGroup mEmptyView;

    private FilterDialogFragment mFilterDialog;
    private FilterDialogFragment.FilterListener mFilterListener;

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private Query mQuery;

    private StudentSearchAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_search_recycler_view_fragment, container, false);
        ButterKnife.bind(this, view);

        //Firestore
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //Get ${LIMIT} class where teacherId == user id
        mQuery = mFirestore.collection("classes")
                .whereEqualTo("student_id", null)
                .orderBy("date", Query.Direction.ASCENDING)
                .limit(LIMIT);

        //RecyclerView
        mAdapter = new StudentSearchAdapter(mQuery, this) {
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

        mFilterListener = new FilterDialogFragment.FilterListener() {
            @Override
            public void onFilter(Filters filters) {
                Query query = mFirestore.collection("classes");

                // Date (equality filter)
                if (filters.hasDate()) {
                    query = query.whereEqualTo(Cours.FIELD_DATE, filters.getDate());
                }

                // Level (equality filter)
                if (filters.hasLevel()) {
                    query = query.whereEqualTo(Cours.FIELD_LEVEL, filters.getLevel());
                }

                // Subject (equality filter)
                if (filters.hasSubject()) {
                    query = query.whereEqualTo(Cours.FIELD_SUBJECT, filters.getSubject());
                }

                // Sort by (orderBy with direction)
                if (filters.hasSortBy()) {
                    query = query.orderBy(filters.getSortBy(), filters.getSortDirection());
                }

                // Limit items
                query = query.limit(LIMIT);

                // Update the query
                mAdapter.setQuery(query);

                // Set header
                mCurrentSearchView.setText(Html.fromHtml(filters.getSearchDescription(getActivity())));
                mCurrentSortByView.setText(filters.getOrderDescription(getActivity()));

                // Save filters
                //        mViewModel.setFilters(filters);
            }
        };

        mFilterDialog = FilterDialogFragment.newInstance(mFilterListener);

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

    @OnClick(R.id.filterBar)
    public void onFilterClicked() {
        // Show the dialog containing filter options
        mFilterDialog.show(getActivity().getSupportFragmentManager(), FilterDialogFragment.TAG);
    }

    @OnClick(R.id.buttonClearFilter)
    public void onClearFilterClicked() {
        mFilterDialog.resetFilters();

        mFilterListener.onFilter(Filters.getDefault());
    }

    @Override
    public void onClassSelected(DocumentSnapshot cours) {
        // Go to the details page for the selected restaurant
        Intent intent = new Intent(getActivity(), StudentSearchDetailActivity.class);
        intent.putExtra(StudentSearchDetailActivity.KEY_CLASS_ID, cours.getId());

        startActivity(intent);
    }

    public static Fragment newInstance(){
        return new StudentSearchRecyclerFragment();
    }

}
