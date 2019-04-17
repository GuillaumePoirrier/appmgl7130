package com.mgl7130.curve.pages.teacher.ui.student.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.mgl7130.curve.R;
import com.mgl7130.curve.databinding.FragmentTeacherStudentDetailBinding;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Student;
import com.mgl7130.curve.pages.teacher.ui.student.viewmodels.TeacherStudentDetailViewModel;
import com.mgl7130.curve.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherStudentDetailFragment extends Fragment {

    public static final String TAG = "TeacherClassDetailAct";
    public static final String KEY_STUDENT_ID = "key_student_id";

    private FragmentTeacherStudentDetailBinding mBinding;
    private TeacherStudentDetailViewModel mViewModel;
    private String classId;

    public static Fragment newInstance() {
        return new TeacherStudentDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_teacher_student_detail, container, false);

        classId = getArguments().getString(KEY_STUDENT_ID);
        if (classId == null) {
            throw new IllegalArgumentException("Must pass extra " + KEY_STUDENT_ID);
        }

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TeacherStudentDetailViewModel.class);
        mBinding.setLifecycleOwner(this);
        mViewModel.setId(classId);

        mViewModel.getStudent().observe(this, student -> mBinding.setStudent(student));
    }

}
