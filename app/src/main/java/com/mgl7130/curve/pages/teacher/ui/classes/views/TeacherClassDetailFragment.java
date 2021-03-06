package com.mgl7130.curve.pages.teacher.ui.classes.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgl7130.curve.R;
import com.mgl7130.curve.databinding.FragmentTeacherClassDetailBinding;
import com.mgl7130.curve.pages.teacher.ui.classes.viewmodels.TeacherClassDetailViewModel;

public class TeacherClassDetailFragment extends Fragment {

    public static final String TAG = "TeacherClassDetailFrag";
    public static final String KEY_CLASS_ID = "key_class_id";

    private TeacherClassDetailViewModel mViewmodel;
    private FragmentTeacherClassDetailBinding mBinding;

    public static Fragment newInstance() {
        return new TeacherClassDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_class_detail, container, false);
        View view = mBinding.getRoot();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String classId = getArguments().getString(KEY_CLASS_ID, null);
        if (classId == null) throw new IllegalArgumentException("Must put extra " + KEY_CLASS_ID);

        mViewmodel = ViewModelProviders.of(this).get(TeacherClassDetailViewModel.class);
        mViewmodel.setClassId(classId);

        mViewmodel.getCours().observe(this, cours -> mBinding.setCours(cours));
        mViewmodel.getStudent().observe(this, student -> mBinding.setStudent(student));

    }
}
