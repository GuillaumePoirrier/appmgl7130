package com.mgl7130.curve.pages.teacher.ui.classes.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Student;
import com.mgl7130.curve.pages.teacher.ui.classes.create.TeacherClassFormActivity;
import com.mgl7130.curve.pages.teacher.ui.student.detail.TeacherStudentDetailFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherClassDetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_detail);

        String classID = getIntent().getExtras().getString(TeacherClassDetailFragment.KEY_CLASS_ID);
        Bundle args = new Bundle();
        args.putString(TeacherClassDetailFragment.KEY_CLASS_ID, classID);

        Fragment fragment = TeacherClassDetailFragment.newInstance();
        fragment.setArguments(args);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detail_fragment, fragment);
        ft.commit();

    }

}
