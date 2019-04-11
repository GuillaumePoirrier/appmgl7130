package com.mgl7130.curve.pages.student.ui.search.list;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mgl7130.curve.R;
import com.mgl7130.curve.common.DataListAdapter;
import com.mgl7130.curve.common.OnItemClickedListener;
import com.mgl7130.curve.databinding.StudentItemClassBinding;
import com.mgl7130.curve.models.Cours;

public class StudentSearchAdapter extends DataListAdapter<Cours, StudentItemClassBinding> {
    public StudentSearchAdapter(OnItemClickedListener<Cours> listener) {
        super(listener);
    }

    @Override
    protected StudentItemClassBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final StudentItemClassBinding binding = DataBindingUtil.inflate(inflater, R.layout.student_item_class, parent, false);
        binding.getRoot().setOnClickListener(v -> {
            final Cours chosen = binding.getCours();
            if (chosen != null) {
                listener.onClicked(chosen);
            }
        });
        return binding;
    }

    @Override
    protected void bind(StudentItemClassBinding binding, Cours item) {
        binding.setCours(item);
    }

}