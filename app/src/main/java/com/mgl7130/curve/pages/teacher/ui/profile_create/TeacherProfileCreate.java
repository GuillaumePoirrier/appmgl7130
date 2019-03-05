package com.mgl7130.curve.pages.teacher.ui.profile_create;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mgl7130.curve.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TeacherProfileCreate extends Fragment {

    private EditText teacherBirthDate, teacherFirstName, teacherFamilyName;
    private Calendar calendar;
    private int Year, Month, Day;
    private DatePickerDialog datePickerDialog;
    private Button saveProfileTeacher;
    private String teacherFirstNameString = "";
    private String teacherFamilyNameString = "";
    private Timestamp teacherBirthDateTimestamp;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public static Fragment newInstance() {
        return new TeacherProfileCreate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_profile_form, container, false);

        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        //Get Firebase database
        db = FirebaseFirestore.getInstance();

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        teacherBirthDate = view.findViewById(R.id.teacher_birth_date);

        teacherBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
                        teacherBirthDate.setText(dateString);
                    }
                };
                datePickerDialog = DatePickerDialog.newInstance(onDateSetListener, Year, Month, Day);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
                datePickerDialog.setTitle(getString(R.string.set_class_date));
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        teacherFirstName = (EditText) view.findViewById(R.id.teacher_first_name);
        teacherFamilyName = (EditText) view.findViewById(R.id.teacher_family_name);
        teacherBirthDate = (EditText) view.findViewById(R.id.teacher_birth_date);

        teacherFirstNameString = teacherFirstName.getText().toString();
        teacherFamilyNameString = teacherFamilyName.getText().toString();


        /*db.collection("teacher").document()
                .set(new Teacher(teacherFirstNameString, teacherFamilyNameString)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(SignUpActivity.this, ProfileChoiceActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e);
                    }
                });
*/


        //TODO : Save profile


        return view;

    }


}
