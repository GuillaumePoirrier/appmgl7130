package com.mgl7130.curve.pages.teacher.ui.classes.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mgl7130.curve.R;
import com.mgl7130.curve.common.DataListAdapter;
import com.mgl7130.curve.common.OnItemClickedListener;
import com.mgl7130.curve.databinding.TeacherItemClassBinding;
import com.mgl7130.curve.models.Cours;

public class TeacherClassAdapter extends DataListAdapter<Cours, TeacherItemClassBinding> {

    public TeacherClassAdapter(OnItemClickedListener<Cours> listener) {
        super(listener);
    }

    @Override
    protected TeacherItemClassBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final TeacherItemClassBinding binding = DataBindingUtil.inflate(inflater, R.layout.teacher_item_class, parent, false);
        binding.getRoot().setOnClickListener(v -> {
            final Cours chosen = binding.getCours();
            if (chosen != null) {
                listener.onClicked(chosen);
            }
        });
        return binding;
    }

    @Override
    protected void bind(TeacherItemClassBinding binding, Cours item) {
        binding.setCours(item);
    }
    
}