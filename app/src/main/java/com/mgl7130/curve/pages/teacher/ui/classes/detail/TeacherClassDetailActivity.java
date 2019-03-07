package com.mgl7130.curve.pages.teacher.ui.classes.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherClassDetailActivity extends AppCompatActivity{

    public static final String TAG = "TeacherClassDetailAct";

    public static final String KEY_RESTAURANT_ID = "key_restaurant_id";

    @BindView(R.id.iv_teacher_class_detail_subject_image)
    ImageView subjectImage;

    @BindView(R.id.tv_teacher_class_detail_subject)
    TextView subject;

    @BindView(R.id.tv_teacher_class_detail_level)
    TextView level;

    @BindView(R.id.tv_teacher_class_detail_date_day)
    TextView dateDay;

    @BindView(R.id.tv_teacher_class_detail_date_month)
    TextView dateMonth;

    @BindView(R.id.tv_teacher_class_detail_date_year)
    TextView dateYear;

    @BindView(R.id.tv_teacher_class_detail_start_time)
    TextView startTime;

    @BindView(R.id.tv_teacher_class_detail_end_time)
    TextView endTime;

    @BindView(R.id.tv_teacher_class_detail_student)
    TextView student;


    private FirebaseFirestore mFirestore;
    private DocumentReference mClassRef;
    private DocumentReference mStudentRef;
    private ListenerRegistration mClassRegistration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_detail);
        ButterKnife.bind(this);

        // Get restaurant ID from extras
        String classId = getIntent().getExtras().getString(KEY_RESTAURANT_ID);
        if (classId == null) {
            throw new IllegalArgumentException("Must pass extra " + KEY_RESTAURANT_ID);
        }

        //init Firestore
        mFirestore =FirebaseFirestore.getInstance();

        mClassRef = mFirestore.collection("classes").document(classId);


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

        switch (subjectName){
            case "Mathematics": {
                subjectImage.setImageDrawable(getDrawable(R.drawable.logo_math));
                break;
            }
            case "Physics": {
                subjectImage.setImageDrawable(getDrawable(R.drawable.logo_physic));
                break;
            }
            case "Chemistry": {
                subjectImage.setImageDrawable(getDrawable(R.drawable.logo_chemistry));
                break;
            }
        }

        level.setText(cours.getLevel().toString());
        startTime.setText((new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(cours.getStartDate().toDate())));
        endTime.setText((new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(cours.getEndDate().toDate())));
        dateDay.setText((new SimpleDateFormat("dd", Locale.CANADA_FRENCH).format(cours.getDate().toDate())));
        dateMonth.setText((new SimpleDateFormat("MMM", Locale.CANADA_FRENCH).format(cours.getDate().toDate())));
        dateYear.setText((new SimpleDateFormat("yyyy", Locale.CANADA_FRENCH).format(cours.getDate().toDate())));

        //test if it has student and if so get the student
        if(cours.getStudent_id() != null){
            mStudentRef = mFirestore.collection("users").document(cours.getStudent_id());
            mStudentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "class:onEvent", e);
                        return;
                    }
                    onStudentLoaded(snapshot.toObject(Student.class));
                }
            });
        } else {
            student.setText(getString(R.string.no_student));
        }
    }


    private void onStudentLoaded(Student student) {

        String studentName = student.getFirstName() + " " + student.getLastName();
        this.student.setText(studentName);

    }

}
