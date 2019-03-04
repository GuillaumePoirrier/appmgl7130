package com.mgl7130.curve.pages.teacher.ui.teacher;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

public class TeacherClassesFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_classes_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       // recyclerView.setAdapter(new TeacherClassesFragment.TeacherClassesFragmentViewAdapter(getListofCour()));


        return view;
    }

    public Teacher getProfile(){
        Teacher teacher = new Teacher("Yves", "Courtois", new Date());
        return teacher;
    }
    public List<Cours> getListofCour(){
        //@TODO get cours for the connecterd teacher
        Student studentA = new Student("Jean", "Dupuis", new Date());
        Student studentB = new Student("Anne", "Muller", new Date());
        Teacher teacher = new Teacher("Yves", "Courtois", new Date());

        List<Cours> cours = new ArrayList<>();
        cours.add(new Cours(new Date(), studentA, teacher, Subject.Mathematics));
        cours.add(new Cours(new Date(), studentB, teacher, Subject.Physics));
        cours.add(new Cours(new Date(), studentA, teacher, Subject.Chemistry));
        cours.add(new Cours(new Date(), studentA, teacher, Subject.Chemistry));
        cours.add(new Cours(new Date(), studentA, teacher, Subject.Chemistry));
        cours.add(new Cours(new Date(), studentA, teacher, Subject.Chemistry));
        cours.add(new Cours(new Date(), studentA, teacher, Subject.Chemistry));
        cours.add(new Cours(new Date(), studentA, teacher, Subject.Chemistry));
        cours.add(new Cours(new Date(), studentA, teacher, Subject.Chemistry));

        return cours;
    }
    public static android.support.v4.app.Fragment newInstance() {
        return new TeacherStudentsRecyclerFragment();
    }

    private class TeacherClassesFragmentViewHolder extends RecyclerView.ViewHolder {

        private CardView mCardView;
        private TextView subject;
        private TextView name;
        private TextView date;
        private ImageView picture;

        public TeacherClassesFragmentViewHolder(View itemView){
            super(itemView);
        }

        public TeacherClassesFragmentViewHolder(LayoutInflater inflater, ViewGroup group){
            super(inflater.inflate(R.layout.teacher_students_card_view, group, false));

            mCardView = itemView.findViewById(R.id.card_container);
            subject = itemView.findViewById(R.id.course);
            name = itemView.findViewById(R.id.student_name);
            date = itemView.findViewById(R.id.date);
            picture = itemView.findViewById(R.id.student_profile_picture);

        }

    }

  /*  public class TeacherClassesFragmentViewAdapter extends RecyclerView.Adapter<TeacherStudentsRecyclerFragment.TeacherClassesFragmentViewHolder> {

       * List<Cours> cours;

        public TeacherStudentsRecyclerViewAdapter(List<Cours> list){
            this.cours = list;
        }

        @Override
        public TeacherClassesFragmentViewHolder.TeacherClassesFragment onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new TeacherStudentsRecyclerFragment.TeacherClassesFragmentViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(TeacherStudentsRecyclerFragment.TeacherClassesFragmentViewHolder holder, int position) {
            holder.subject.setText(cours.get(position).getSubject().toString());
            holder.name.setText(cours.get(position).getStudentFullName());
            holder.date.setText(new Date().toString());
        }

        @Override
        public int getItemCount() {
            return cours.size();
        }
    }*/




}
