package com.mgl7130.curve.pages.teacher.ui.teacher;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Teacher;

import java.util.Date;

import static com.mgl7130.curve.R.layout.teacher_profile_fragment;

public class TeacherProfileFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(teacher_profile_fragment, container, false);
        return view;
    }

    public Teacher getProfile(){
        Teacher teacher = new Teacher("Yves", "Courtois", new Date());
        return teacher;
    }




    public static android.support.v4.app.Fragment newInstance() {
        return new android.support.v4.app.Fragment();
    }

   /* private class TeacherProfileViewHolder {

        public TeacherProfileViewHolder(View itemView){
            super(itemView);
        }

        public TeacherProfileViewHolder(LayoutInflater inflater, ViewGroup group){
            super(inflater.inflate(R.layout.teacher_profile_fragment, group, false));

        }

    }*/
}
