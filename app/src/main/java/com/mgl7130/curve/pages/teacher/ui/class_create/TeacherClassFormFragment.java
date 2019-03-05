package com.mgl7130.curve.pages.teacher.ui.class_create;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Level;
import com.mgl7130.curve.models.Subject;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TeacherClassFormFragment extends Fragment{

    private Button createClass;
    private Spinner subject, level;
    private EditText date, startHour, endHour;
    private Calendar calendar ;
    private DatePickerDialog datePickerDialog ;
    private TimePickerDialog timePickerDialog ;
    private int Year, Month, Day ;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_students_class_form_fragment, container, false);

        //init Firebase instances
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //init a Calendar
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        //get view elements
        createClass = view.findViewById(R.id.button_create_class);
        subject = view.findViewById(R.id.spinner_subject);
        level = view.findViewById(R.id.spinner_level);
        date = view.findViewById(R.id.et_class_date);
        startHour = view.findViewById(R.id.start_time);
        endHour = view.findViewById(R.id.end_time);

        //Fill the spinners to match enum data
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, Subject.stringValues());
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, Level.stringValues());

        subject.setAdapter(subjectAdapter);
        level.setAdapter(levelAdapter);

        //create listners on date and time EditText to start pickers Dialogs
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
                        date.setText(dateString);
                    }
                };
                datePickerDialog = DatePickerDialog.newInstance(onDateSetListener, Year, Month, Day);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
                datePickerDialog.setTitle(getString(R.string.set_class_date));
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        startHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String time = hourOfDay + ":" + minute;
                        startHour.setText(time);
                    }
                };
                timePickerDialog = TimePickerDialog.newInstance(onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
                timePickerDialog.setTitle(getString(R.string.set_class_start_time));
                timePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        endHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String time = hourOfDay + ":" + minute;
                        endHour.setText(time);
                    }
                };
                timePickerDialog = TimePickerDialog.newInstance(onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
                timePickerDialog.setTitle(getString(R.string.set_class_start_time));
                timePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            }
        });

        //Test fields values before completing the form
        createClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verifyForm(date, startHour, endHour)){
                    try {
                        createDbClass(date, startHour, endHour, subject, level);
                    } catch (Exception e) {
                        System.out.print(e);
                    }

                }
            }
        });

        return view;
    }

    public static android.support.v4.app.Fragment newInstance() {
        return new TeacherClassFormFragment();
    }

    public boolean verifyForm(EditText date, EditText startHour, EditText endHour){
        if (date.getText() == null){
            Toast.makeText(getActivity(), getString(R.string.select_date_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startHour.getText() == null){
            Toast.makeText(getActivity(), getString(R.string.select_startHour_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (endHour.getText() == null){
            Toast.makeText(getActivity(), getString(R.string.select_endHour_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createDbClass(EditText date, EditText startHour, EditText endHour, Spinner subject, Spinner level) throws ParseException {
        //generate timestamp from time string
        String[] time = startHour.getText().toString().split(":");
        Timestamp start = new Timestamp((Long.parseLong(time[0])*3600)+(Long.parseLong(time[1])*60),0);
        time = endHour.getText().toString().split(":");
        Timestamp end = new Timestamp((Long.parseLong(time[0])*3600)+(Long.parseLong(time[1])*60),0);

        //Generate timestanp from date
        Timestamp classDate = new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(date.getText().toString()));

        //Get values from Spinners
        Subject subject1 = Subject.getSubject(subject.getSelectedItem().toString());
        Level level1 = Level.getSubject(level.getSelectedItem().toString());

        //createClass object from values
        Cours cours = new Cours(mAuth.getUid(), subject1, level1, classDate, start, end);

        //add Class object to Db
        db.collection("classes").add(cours);

    }
}

