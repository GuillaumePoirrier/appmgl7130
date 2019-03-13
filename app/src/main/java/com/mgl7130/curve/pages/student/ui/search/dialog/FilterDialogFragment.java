package com.mgl7130.curve.pages.student.ui.search.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Query;
import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.models.Level;
import com.mgl7130.curve.models.Subject;
import com.mgl7130.curve.pages.student.ui.search.model.Filters;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Dialog Fragment containing filter form.
 */
public class FilterDialogFragment extends DialogFragment {

    public static final String TAG = "FilterDialog";

    public interface FilterListener {

        void onFilter(Filters filters);

    }

    private View mRootView;

    @BindView(R.id.spinnerSubject)
    Spinner mSubjectSpinner;

    @BindView(R.id.spinnerLevel)
    Spinner mLevelSpinner;

    @BindView(R.id.spinnerSort)
    Spinner mSortSpinner;

    @BindView(R.id.textviewDate)
    TextView mDateTextView;

    private static FilterListener  mFilterListener;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private ArrayAdapter<String> subjectAdapter;
    private ArrayAdapter<String> levelAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dialog_filters, container, false);
        ButterKnife.bind(this, mRootView);

        //init spinners
        List<String> subjectList = new ArrayList<String>() {{add(getString(R.string.value_any_subject));addAll(Subject.stringValues());}};
        subjectAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, subjectList);
        mSubjectSpinner.setAdapter(subjectAdapter);
        List<String> levelList = new ArrayList<String>() {{add(getString(R.string.value_any_level));addAll(Level.stringValues());}};
        levelAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, levelList);
        mLevelSpinner.setAdapter(levelAdapter);

        calendar = Calendar.getInstance();

        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FilterListener) {
            mFilterListener = (FilterListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @OnClick(R.id.buttonSearch)
    public void onSearchClicked() {
        if (mFilterListener != null) {
            mFilterListener.onFilter(getFilters());
        }

        dismiss();
    }

    @OnClick(R.id.buttonCancel)
    public void onCancelClicked() {
        dismiss();
    }

    @OnClick(R.id.textviewDate)
    public void onDateClicked(View view){
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                String dateString = new SimpleDateFormat("dd MMMM yyyy", Locale.CANADA_FRENCH).format(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
                mDateTextView.setText(dateString);
            }
        };
        datePickerDialog = DatePickerDialog.newInstance(onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
        datePickerDialog.setMinDate(calendar);
        datePickerDialog.setTitle(getString(R.string.set_class_date));
        datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
    }

    @Nullable
    private String getSelectedSubject() {
        String selected = (String) mSubjectSpinner.getSelectedItem();
        if (getString(R.string.value_any_subject).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedLevel() {
        String selected = (String) mLevelSpinner.getSelectedItem();
        if (getString(R.string.value_any_level).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    private String getSelectedDate() {
        String selected = (String) mDateTextView.getText();
        if (getString(R.string.value_any_date).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedSortBy() {
        String selected = (String) mSortSpinner.getSelectedItem();
        if (getString(R.string.sort_by_date).equals(selected)) {
            return Cours.FIELD_DATE;
        } if (getString(R.string.sort_by_level).equals(selected)) {
            return Cours.FIELD_LEVEL;
        } if (getString(R.string.sort_by_subject).equals(selected)) {
            return Cours.FIELD_SUBJECT;
        }

        return null;
    }

    @Nullable
    private Query.Direction getSortDirection() {
        String selected = (String) mSortSpinner.getSelectedItem();
        if (getString(R.string.sort_by_date).equals(selected)) {
            return Query.Direction.ASCENDING;
        } if (getString(R.string.sort_by_level).equals(selected)) {
            return Query.Direction.ASCENDING;
        } if (getString(R.string.sort_by_subject).equals(selected)) {
            return Query.Direction.DESCENDING;
        }

        return null;
    }

    public void resetFilters() {
        if (mRootView != null) {
            mSubjectSpinner.setSelection(0);
            mLevelSpinner.setSelection(0);
            mSortSpinner.setSelection(0);
            mDateTextView.setText("All dates");
        }
    }

    public Filters getFilters() {
        Filters filters = new Filters();

        if (mRootView != null) {
            filters.setSubject(getSelectedSubject());
            filters.setLevel(getSelectedLevel());
            try {
                filters.setDate(new Timestamp((new SimpleDateFormat("dd MMM yyyy", Locale.CANADA_FRENCH).parse(getSelectedDate()))));
            } catch (Exception e) { }
            filters.setSortBy(getSelectedSortBy());
            filters.setSortDirection(getSortDirection());
        }

        return filters;
    }

    public static FilterDialogFragment newInstance(FilterListener listener) {
        mFilterListener = listener;
        return new FilterDialogFragment();
    }

}
