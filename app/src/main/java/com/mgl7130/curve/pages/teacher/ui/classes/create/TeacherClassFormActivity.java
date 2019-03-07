package com.mgl7130.curve.pages.teacher.ui.classes.create;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherClassFormActivity extends AppCompatActivity {

    public static final String TAG = "TeacherClassFormActivity";

    @BindView(R.id.spinner_subject)
    Spinner subject;

    @BindView(R.id.spinner_level)
    Spinner level;

    @BindView(R.id.et_class_date)
    EditText date;

    @BindView(R.id.start_time)
    EditText startHour;

    @BindView(R.id.end_time)
    EditText endHour;

    @BindView(R.id.toolbar_title)
    TextView toolbarTittle;


    private DatePickerDialog datePickerDialog ;
    private TimePickerDialog timePickerDialog ;
    private Calendar calendar ;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_class_form);
        ButterKnife.bind(this);

        //Set toolbar tittle
        toolbarTittle.setText(getString(R.string.create_class));

        //init Calendar
        calendar = Calendar.getInstance();

        //init Firebase instances
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //Fill the spinners to match enum data
        subject.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Subject.stringValues()));
        level.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Level.stringValues()));

    }

    @OnClick(R.id.et_class_date)
    public void onDateClicked(View view){
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                String dateString = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA_FRENCH).format(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
                date.setText(dateString);
            }
        };
        datePickerDialog = DatePickerDialog.newInstance(onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
        datePickerDialog.setMinDate(calendar);
        datePickerDialog.setTitle(getString(R.string.set_class_date));
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }


    @OnClick(R.id.start_time)
    public void onStartTimeClicked(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                startHour.setText(formatHour(hourOfDay, minute));
            }
        };
        timePickerDialog = TimePickerDialog.newInstance(onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
        timePickerDialog.setTitle(getString(R.string.set_class_start_time));
        timePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    @OnClick(R.id.end_time)
    public void onEndTimeClicked(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                endHour.setText(formatHour(hourOfDay, minute));
            }
        };
        timePickerDialog = TimePickerDialog.newInstance(onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
        timePickerDialog.setTitle(getString(R.string.set_class_start_time));
        timePickerDialog.show(getFragmentManager(), "DatePickerDialog");
    }

    @OnClick(R.id.button_create_class)
    public void onCreateClassClicked(View view){
        if (verifyForm(date, startHour, endHour)){
            try {
                createDbClass(date, startHour, endHour, subject, level);
                finish();
            } catch (Exception e) {
                System.out.print(e);
            }

        }
    }

    public boolean verifyForm(EditText date, EditText startHour, EditText endHour){
        if (date.getText().equals("")){
            Toast.makeText(this, getString(R.string.select_date_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (startHour.getText().equals("")){
            Toast.makeText(this, getString(R.string.select_startHour_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (endHour.getText().equals("")){
            Toast.makeText(this, getString(R.string.select_endHour_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void createDbClass(EditText date, EditText startHour, EditText endHour, Spinner subject, Spinner level) throws ParseException {
        //generate timestamp from time string
        Timestamp start = new Timestamp(new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).parse(startHour.getText().toString()));
        Timestamp end = new Timestamp(new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).parse(endHour.getText().toString()));

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

    private String formatHour(int hour, int minutes) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH);
        String timeString = "";
        try {
            timeString = new SimpleDateFormat("HH:mm", Locale.CANADA).format(simpleDateFormat.parse(hour + ":" + minutes));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;
    }
}

