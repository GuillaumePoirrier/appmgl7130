package com.mgl7130.curve.pages.teacher.ui.student.views;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgl7130.curve.R;
import com.mgl7130.curve.databinding.TeacherStudentRecyclerViewFragmentBinding;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.pages.teacher.ui.student.adapter.TeacherStudentAdapter;
import com.mgl7130.curve.pages.teacher.ui.student.viewmodels.TeacherStudentRecyclerViewModel;

public class TeacherStudentRecyclerFragment extends Fragment {

    public static final String TAG = "StudentSearchRecyclerFragment";
    public static final String CHANNEL_ID = "new_student";

    private TeacherStudentAdapter mAdapter;
    private TeacherStudentRecyclerViewFragmentBinding mBinding;
    private TeacherStudentRecyclerViewModel mViewModel;
    private boolean hasDetailLayout = false;

    public static Fragment newInstance() {
        return new TeacherStudentRecyclerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.teacher_student_recycler_view_fragment, container, false);
        mBinding.setNoData(true);
        View view = mBinding.getRoot();

        if (view.findViewById(R.id.studentdetailLayout) != null) hasDetailLayout = true;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(TeacherStudentRecyclerViewModel.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setViewmodel(mViewModel);

        initRecycler();

        mViewModel.getClasses().observe(this, classes -> {
            if (classes.size() > 0) mBinding.setNoData(false);
            mAdapter.replace(classes);
        });

        mViewModel.getNotificationEvent().observe(this, cours -> {

        });

    }


    private void initRecycler() {
        mAdapter = new TeacherStudentAdapter(this::onClassSelected);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setAdapter(mAdapter);
    }


    public void onClassSelected(Cours cours) {

        if (hasDetailLayout) {
            getActivity().findViewById(R.id.studentdetailLayout).setVisibility(View.VISIBLE);

            Bundle args = new Bundle();
            args.putString(TeacherStudentDetailFragment.KEY_STUDENT_ID, cours.id);

            Fragment fragment = TeacherStudentDetailFragment.newInstance();
            fragment.setArguments(args);

            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.studentdetailLayout, fragment);
            ft.commit();

        } else {
            // Go to the details page for the selected restaurant
            Intent intent = new Intent(getActivity(), TeacherStudentDetailActivity.class);
            intent.putExtra(TeacherStudentDetailFragment.KEY_STUDENT_ID, cours.id);

            startActivity(intent);
        }

    }

}
