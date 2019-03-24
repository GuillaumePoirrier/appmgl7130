package com.mgl7130.curve.pages.teacher.ui.student.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.TimeUnit;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Student;
import com.mgl7130.curve.pages.teacher.ui.profile_create.TeacherProfileCreate;
import com.mgl7130.curve.pages.teacher.ui.student.list.TeacherStudentRecyclerFragment;

import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherStudentDetailFragment extends Fragment {

    public static final String TAG = "TeacherClassDetailAct";
    public static final String KEY_STUDENT_ID = "key_student_id";

    @BindView(R.id.iv_teacher_student_detail_student_image)
    ImageView subjectImage;

    @BindView(R.id.tv_teacher_student_detail_name)
    TextView name;

    @BindView(R.id.tv_teacher_student_detail_age)
    TextView age;

    @BindView(R.id.tv_teacher_student_detail_description_label)
    TextView description;


    private FirebaseFirestore mFirestore;
    private DocumentReference mStudentRef;
    private DocumentReference mClassRef;
    private ListenerRegistration mClassRegistration;
    private String classId;
    private String studentId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_student_detail, container, false);
        ButterKnife.bind(this, view);

        classId = getArguments().getString(KEY_STUDENT_ID);
        if (classId == null) {
            throw new IllegalArgumentException("Must pass extra " + KEY_STUDENT_ID);
        }

        //init Firestore
        mFirestore =FirebaseFirestore.getInstance();

//
        mClassRef = mFirestore.collection("classes").document(classId);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mClassRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
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

    private void onClassLoaded(final Cours cours){
        if (cours.getStudent_id() != null){
            mStudentRef = mFirestore.collection("users").document(cours.getStudent_id());
            mStudentRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w(TAG, "class:onEvent", e);
                        return;
                    }
                    onStudentLoaded(snapshot.toObject(Student.class), cours.getStudent_id());
                }
            });
        }
    }

    private void onStudentLoaded(Student student, String studentId) {
        if (student != null) {
            String fullname = student.getFirstName() + " " + student.getLastName();
            name.setText(fullname);
            if (student.hasBirthDate()){
                age.setText(new SimpleDateFormat("dd MMMM yyyy", Locale.CANADA_FRENCH).format(student.getBirthDate().toDate()));
            }
            if (student.hasDescription()) description.setText(student.getDescription());
            FirebaseStorage.getInstance().getReference().child("curve/" + studentId + ".jpg")
                    .getBytes(100000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap profilePicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    subjectImage.setImageBitmap(profilePicture);
                }
            });
        }
    }

    public static Fragment newInstance(){
        return new TeacherStudentDetailFragment();
    }

}
