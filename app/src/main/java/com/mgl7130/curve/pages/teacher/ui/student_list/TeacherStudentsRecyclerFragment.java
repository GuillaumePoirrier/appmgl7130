package com.mgl7130.curve.pages.teacher.ui.student_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Student;
import com.mgl7130.curve.models.Subject;
import com.mgl7130.curve.models.Teacher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherStudentsRecyclerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_students_recycler_view_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new TeacherStudentsRecyclerViewAdapter(getListofCour()));

        return view;
    }

    public List<Cours> getListofCour(){
        //@TODO get all classes from theacher where student is not null
        return null;
    }

    public static Fragment newInstance() {
        return new TeacherStudentsRecyclerFragment();
    }

    private class TeacherStudentsRecyclerViewHolder extends RecyclerView.ViewHolder {

        private CardView mCardView;
        private TextView subject;
        private TextView name;
        private TextView date;
        private ImageView picture;

        public TeacherStudentsRecyclerViewHolder(View itemView){
            super(itemView);
        }

        public TeacherStudentsRecyclerViewHolder(LayoutInflater inflater, ViewGroup group){
            super(inflater.inflate(R.layout.teacher_students_card_view, group, false));

            mCardView = itemView.findViewById(R.id.card_container);
            subject = itemView.findViewById(R.id.course);
            name = itemView.findViewById(R.id.student_name);
            date = itemView.findViewById(R.id.date);
            picture = itemView.findViewById(R.id.student_profile_picture);

        }

    }

    public class TeacherStudentsRecyclerViewAdapter extends RecyclerView.Adapter<TeacherStudentsRecyclerViewHolder> {

        List<Cours> cours;

        public TeacherStudentsRecyclerViewAdapter(List<Cours> list){
            this.cours = list;
        }

        @Override
        public TeacherStudentsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new TeacherStudentsRecyclerViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(TeacherStudentsRecyclerViewHolder holder, int position) {
            holder.subject.setText(cours.get(position).getSubject().toString());
            holder.date.setText(new Date().toString());
        }

        @Override
        public int getItemCount() {
            return cours.size();
        }
    }

}
