package com.mgl7130.curve.pages.teacher.ui.classes.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgl7130.curve.R;
import com.mgl7130.curve.databinding.TeacherClassRecyclerViewFragmentBinding;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.pages.teacher.ui.classes.adapter.TeacherClassAdapter;
import com.mgl7130.curve.pages.teacher.ui.classes.viewmodels.TeacherClassRecyclerViewModel;

public class TeacherClassRecyclerFragment extends Fragment{

    public static final String TAG = "StudentSearchRecyclerF";

    private TeacherClassAdapter mAdapter;
    private TeacherClassRecyclerViewFragmentBinding mBinding;
    private TeacherClassRecyclerViewModel mViewmodel;
    private boolean hasDetailLayout = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.teacher_class_recycler_view_fragment, container, false);
        mBinding.setNoData(true);
        View view = mBinding.getRoot();

        if (view.findViewById(R.id.classdetailLayout) != null) hasDetailLayout = true;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewmodel = ViewModelProviders.of(this).get(TeacherClassRecyclerViewModel.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setViewmodel(mViewmodel);

        initRecycler();

        mViewmodel.getClasses().observe(this, classes -> {
            if (classes.size() > 0) mBinding.setNoData(false);
            mAdapter.replace(classes);
        });

        mViewmodel.addClassActivity.observe(this, teacherClassFormActivity -> startActivity(new Intent(getActivity(), teacherClassFormActivity)));
    }

    private void initRecycler() {
        mAdapter = new TeacherClassAdapter(this::onClassSelected);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    public void onClassSelected(Cours cours) {
        // Go to the details page for the selected restaurant
        if(hasDetailLayout){
            getActivity().findViewById(R.id.classdetailLayout).setVisibility(View.VISIBLE);

            Bundle args = new Bundle();
            args.putString(TeacherClassDetailFragment.KEY_CLASS_ID, cours.id);

            Fragment fragment = TeacherClassDetailFragment.newInstance();
            fragment.setArguments(args);

            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.classdetailLayout, fragment);
            ft.commit();

        } else {
            // Go to the details page for the selected restaurant
            Intent intent = new Intent(getActivity(), TeacherClassDetailActivity.class);
            intent.putExtra(TeacherClassDetailFragment.KEY_CLASS_ID, cours.id);

            startActivity(intent);
        }
    }

    public static Fragment newInstance(){
        return new TeacherClassRecyclerFragment();
    }

}
