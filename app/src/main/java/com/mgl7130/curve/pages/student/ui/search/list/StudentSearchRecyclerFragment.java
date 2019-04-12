package com.mgl7130.curve.pages.student.ui.search.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgl7130.curve.R;
import com.mgl7130.curve.databinding.StudentSearchRecyclerViewFragmentBinding;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.pages.student.ui.search.detail.StudentSearchDetailActivity;
import com.mgl7130.curve.pages.student.ui.search.dialog.FilterDialogFragment;
import com.mgl7130.curve.pages.student.ui.search.model.Filters;
import com.mgl7130.curve.pages.student.ui.search.viewmodel.StudentSearchFilterViewModel;

public class StudentSearchRecyclerFragment extends Fragment {

    public static final String TAG = "StudentSearchRecycler";

    private StudentSearchAdapter mAdapter;
    private StudentSearchRecyclerViewFragmentBinding mBinding;
    private FilterDialogFragment mFilterDialog;
    private StudentSearchFilterViewModel mViewModel;

    public static Fragment newInstance() {
        return new StudentSearchRecyclerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.student_search_recycler_view_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(StudentSearchFilterViewModel.class);
        initRecycler();

        mBinding.setHandler(clear -> {
            if (clear) {
                mFilterDialog.resetFilters();
                onFilter(Filters.getDefault());
            } else {
                mFilterDialog.show(getActivity().getSupportFragmentManager(), FilterDialogFragment.TAG);
            }
        });

        mViewModel.getClasses().observe(this, listResource -> {
            if (listResource.isSuccessful()) {
                mAdapter.replace(listResource.data());
            }
        });

        mFilterDialog = FilterDialogFragment.newInstance(this::onFilter);

    }

    private void initRecycler() {
        mAdapter = new StudentSearchAdapter(this::onClassSelected);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    public void onClassSelected(Cours cours) {
        Intent intent = new Intent(getActivity(), StudentSearchDetailActivity.class);
        intent.putExtra(StudentSearchDetailActivity.KEY_CLASS_ID, cours.id);

        startActivity(intent);
    }

    public void onFilter(Filters filters) {
        Log.d(TAG, "onFilter");
        // Set header
        mBinding.textCurrentSearch.setText(Html.fromHtml(filters.getSearchDescription(getActivity())));
        mBinding.textCurrentSortBy.setText(filters.getOrderDescription(getActivity()));

        // Save filters
        mViewModel.setFilters(filters);
    }
}
