package com.mgl7130.curve.pages.teacher.ui.student.list;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.mgl7130.curve.R;
import com.mgl7130.curve.adapter.FirestoreAdapter;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Student;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherStudentAdapter extends FirestoreAdapter<TeacherStudentAdapter.ViewHolder> {

    public interface OnClassSelectedListener {
        void onClassSelected(DocumentSnapshot restaurant);
    }

    private OnClassSelectedListener mListener;

    public TeacherStudentAdapter(Query query, OnClassSelectedListener listener){
        super(query);
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.teacher_students_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private FirebaseFirestore mFirestore;
        private FirebaseStorage mStorage;
        private FirebaseAuth mAuth;
        private DocumentReference mStudentRef;

        @BindView(R.id.item_iv_student_image)
        ImageView studentPicture;

        @BindView(R.id.item_tv_student_name)
        TextView studentName;

        @BindView(R.id.item_tv_subject)
        TextView subject;

        @BindView(R.id.item_tv_level)
        TextView level;

        @BindView(R.id.item_tv_from)
        TextView startTime;

        @BindView(R.id.item_tv_to)
        TextView endTime;

        @BindView(R.id.item_tv_date_day)
        TextView dateDay;

        @BindView(R.id.item_tv_date_month)
        TextView dateMonth;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mFirestore = FirebaseFirestore.getInstance();
            mStorage = FirebaseStorage.getInstance();
            mAuth = FirebaseAuth.getInstance();
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnClassSelectedListener listener) {

            Cours cours = snapshot.toObject(Cours.class);
            Resources resources = itemView.getResources();

            subject.setText(cours.getSubject().toString());
            level.setText(cours.getLevel().toString());
            startTime.setText((new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(cours.getStartDate().toDate())));
            endTime.setText((new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(cours.getEndDate().toDate())));
            dateDay.setText((new SimpleDateFormat("dd", Locale.CANADA_FRENCH).format(cours.getDate().toDate())));
            dateMonth.setText((new SimpleDateFormat("MMM", Locale.CANADA_FRENCH).format(cours.getDate().toDate())));

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
                studentName.setText("Error no Student Found");
            }

            //Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("click");
                    if (listener != null) {
                        listener.onClassSelected(snapshot);
                    }
                }
            });
        }

        private void onStudentLoaded(Student student) {
            String studentName = "Error no Student Found";
            if (student != null) {
                studentName = student.getFirstName() + " " + student.getLastName();
            }
            this.studentName.setText(studentName);

            mStorage.getReference().child("curve/" + mAuth.getCurrentUser().getUid() + ".jpg")
                    .getBytes(100000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap profilePicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    studentPicture.setImageBitmap(profilePicture);
                }
            });
        }

    }


    
}