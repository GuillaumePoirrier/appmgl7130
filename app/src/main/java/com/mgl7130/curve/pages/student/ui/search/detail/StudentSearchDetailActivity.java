package com.mgl7130.curve.pages.student.ui.search.detail;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;
import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Student;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentSearchDetailActivity extends AppCompatActivity{

    public static final String TAG = "StudentClassDetailAct";
    public static final String KEY_CLASS_ID = "key_class_id";

    @BindView(R.id.tv_student_class_detail_subject)
    TextView subject;

    @BindView(R.id.tv_student_class_detail_level)
    TextView level;

    @BindView(R.id.tv_student_class_detail_date_day)
    TextView dateDay;

    @BindView(R.id.tv_student_class_detail_date_month)
    TextView dateMonth;

    @BindView(R.id.tv_student_class_detail_date_year)
    TextView dateYear;

    @BindView(R.id.tv_student_class_detail_start_time)
    TextView startTime;

    @BindView(R.id.tv_student_class_detail_end_time)
    TextView endTime;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;


    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    private DocumentReference mClassRef;
    private DocumentReference mStudentRef;
    private ListenerRegistration mClassRegistration;
    private String classId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_search_detail);
        ButterKnife.bind(this);

        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        // Get restaurant ID from extras
        classId = getIntent().getExtras().getString(KEY_CLASS_ID);
        if (classId == null) {
            throw new IllegalArgumentException("Must pass extra " + KEY_CLASS_ID);
        }

        //init Firestore
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mClassRef = mFirestore.collection("classes").document(classId);


    }

    @OnClick(R.id.buttonCancel)
    public void onCancelClicked(){
        onBackPressed();
    }
    @OnClick(R.id.buttonParticipate)
    public void onParticipateClicked(){
        showConfirmDialog();
    }


    @Override
    public void onStart() {
        super.onStart();

        mClassRegistration = mClassRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "class:onEvent", e);
                    return;
                }

                onClassLoaded(snapshot.toObject(Cours.class));
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mClassRegistration != null) {
            mClassRegistration.remove();
            mClassRegistration = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
    }


    private void onClassLoaded(Cours cours) {

        String subjectName = cours.getSubject().toString();
        subject.setText(subjectName);

        level.setText(cours.getLevel().toString());
        startTime.setText((new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(cours.getStartDate().toDate())));
        endTime.setText((new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(cours.getEndDate().toDate())));
        dateDay.setText((new SimpleDateFormat("dd", Locale.CANADA_FRENCH).format(cours.getDate().toDate())));
        dateMonth.setText((new SimpleDateFormat("MMM", Locale.CANADA_FRENCH).format(cours.getDate().toDate())));
        dateYear.setText((new SimpleDateFormat("yyyy", Locale.CANADA_FRENCH).format(cours.getDate().toDate())));
    }

    private void participateToClass(){
        Map<String,Object> studentMap = new HashMap<String,Object>() {{
            put("student_id", mAuth.getCurrentUser().getUid());
            put("hasStudent", true);
        }};
        mFirestore.collection("classes").document(classId).update(studentMap);
        finish();
    }

    private void showConfirmDialog() throws Resources.NotFoundException {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.confirm_paticipation))
                .setMessage(getResources().getString(R.string.confirm_paticipation_question))
                .setPositiveButton(getResources().getString(R.string.confirm_participate),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                participateToClass();
                            }
                        })
                .setNegativeButton(getResources().getString(R.string.cancel), null).show();
    }
}
