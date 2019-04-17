package com.mgl7130.curve.pages.teacher.ui.student.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.mgl7130.curve.R;
import com.mgl7130.curve.common.DataListAdapter;
import com.mgl7130.curve.common.OnItemClickedListener;
import com.mgl7130.curve.databinding.TeacherStudentsCardViewBinding;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Student;

public class TeacherStudentAdapter extends DataListAdapter<Cours, TeacherStudentsCardViewBinding> {

    public TeacherStudentAdapter(OnItemClickedListener<Cours> listener) {
        super(listener);
    }

    @Override
    protected TeacherStudentsCardViewBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final TeacherStudentsCardViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.teacher_students_card_view, parent, false);
        binding.getRoot().setOnClickListener(v -> {
            final Cours chosen = binding.getCours();
            if (chosen != null) {
                listener.onClicked(chosen);
            }
        });
        return binding;
    }

    @Override
    protected void bind(TeacherStudentsCardViewBinding binding, Cours item) {
        binding.setCours(item);
        loadStudent(binding, item);
    }

    private void loadStudent(TeacherStudentsCardViewBinding binding, Cours item) {
        FirebaseFirestore.getInstance().collection("users").document(item.getStudent_id())
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) {
                        Log.w("TeacherStudentAdapter", e);
                        return;
                    }
                    Student student = snapshot.toObject(Student.class).withId(snapshot.getId());
                    binding.setStudent(student);
                });
    }
}