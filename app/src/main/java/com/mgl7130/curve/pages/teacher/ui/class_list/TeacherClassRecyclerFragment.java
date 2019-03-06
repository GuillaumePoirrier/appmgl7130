package com.mgl7130.curve.pages.teacher.ui.class_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Cours;
import java.util.List;

public class TeacherClassRecyclerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_students_recycler_view_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TeacherClassRecyclerViewAdapter(getListofCour()));

        return view;
    }

    public List<Cours> getListofCour(){
        //@TODO get all classes from teacher
        return null;
    }

    public static Fragment newInstance(){
        return new TeacherClassRecyclerFragment();
    }

    private class TeacherClassRecyclerViewHolder extends RecyclerView.ViewHolder {

        public TeacherClassRecyclerViewHolder(View itemView){
            super(itemView);
        }

        public TeacherClassRecyclerViewHolder(LayoutInflater inflater, ViewGroup group){
            super(inflater.inflate(R.layout.teacher_students_card_view, group, false));
        }

    }

    public class TeacherClassRecyclerViewAdapter extends RecyclerView.Adapter<TeacherClassRecyclerViewHolder> {

        List<Cours> cours;

        public TeacherClassRecyclerViewAdapter(List<Cours> list){
            this.cours = list;
        }

        @Override
        public TeacherClassRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new TeacherClassRecyclerViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(TeacherClassRecyclerViewHolder holder, int position) {}

        @Override
        public int getItemCount() {
            return cours.size();
        }
    }

}
