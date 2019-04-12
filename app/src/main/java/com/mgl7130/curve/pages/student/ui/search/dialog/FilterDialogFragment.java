package com.mgl7130.curve.pages.student.ui.search.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Query;
import com.mgl7130.curve.R;
import com.mgl7130.curve.databinding.DialogFiltersBinding;
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

/**
 * Dialog Fragment containing filter form.
 */
public class FilterDialogFragment extends DialogFragment {

    public static final String TAG = "FilterDialog";
    private static FilterListener mFilterListener;
    private View mRootView;
    private DialogFiltersBinding mBinding;
    private DatePickerDialog datePickerDialog;

    public static FilterDialogFragment newInstance(FilterListener listener) {
        mFilterListener = listener;
        return new FilterDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_filters, container, false);
        mBinding.setHandler(doCancel -> {
            if (!doCancel) {
                if (mFilterListener != null) {
                    mFilterListener.onFilter(getFilters());
                }
            }
            dismiss();
        });

        mRootView = mBinding.getRoot();
        initSpinners();
        initOnDateClicked();

        return mRootView;
    }

    public void initSpinners() {
        List<String> subjectList = new ArrayList<String>() {{
            add(getString(R.string.value_any_subject));
            addAll(Subject.stringValues());
        }};
        mBinding.spinnerSubject.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, subjectList));
        List<String> levelList = new ArrayList<String>() {{
            add(getString(R.string.value_any_level));
            addAll(Level.stringValues());
        }};
        mBinding.spinnerLevel.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, levelList));
    }

    public void initOnDateClicked() {
        Calendar calendar = Calendar.getInstance();
        mBinding.textviewDate.setOnClickListener(view -> {
            TextView tv = (TextView) view;
            DatePickerDialog.OnDateSetListener onDateSetListener = (view1, year, monthOfYear, dayOfMonth) -> {
                String dateString = new SimpleDateFormat("dd MMMM yyyy", Locale.CANADA_FRENCH).format(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
                tv.setText(dateString);
            };
            datePickerDialog = DatePickerDialog.newInstance(onDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setAccentColor(getResources().getColor(R.color.color_blue));
            datePickerDialog.setMinDate(calendar);
            datePickerDialog.setTitle(getString(R.string.set_class_date));
            datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void onDateClicked(View view) {

    }

    @Nullable
    private String getSelectedSubject() {
        String selected = (String) mBinding.spinnerSubject.getSelectedItem();
        if (getString(R.string.value_any_subject).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedLevel() {
        String selected = (String) mBinding.spinnerLevel.getSelectedItem();
        if (getString(R.string.value_any_level).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    private String getSelectedDate() {
        String selected = (String) mBinding.textviewDate.getText();
        if (getString(R.string.value_any_date).equals(selected)) {
            return null;
        } else {
            return selected;
        }
    }

    @Nullable
    private String getSelectedSortBy() {
        String selected = (String) mBinding.spinnerSort.getSelectedItem();
        if (getString(R.string.sort_by_date).equals(selected)) {
            return Cours.FIELD_DATE;
        }
        if (getString(R.string.sort_by_level).equals(selected)) {
            return Cours.FIELD_LEVEL;
        }
        if (getString(R.string.sort_by_subject).equals(selected)) {
            return Cours.FIELD_SUBJECT;
        }

        return null;
    }

    @Nullable
    private Query.Direction getSortDirection() {
        String selected = (String) mBinding.spinnerSort.getSelectedItem();
        if (getString(R.string.sort_by_date).equals(selected)) {
            return Query.Direction.ASCENDING;
        }
        if (getString(R.string.sort_by_level).equals(selected)) {
            return Query.Direction.ASCENDING;
        }
        if (getString(R.string.sort_by_subject).equals(selected)) {
            return Query.Direction.DESCENDING;
        }

        return null;
    }

    public void resetFilters() {
        if (mRootView != null) {
            mBinding.spinnerSubject.setSelection(0);
            mBinding.spinnerLevel.setSelection(0);
            mBinding.spinnerSort.setSelection(0);
            mBinding.textviewDate.setText("All dates");
        }
    }

    public Filters getFilters() {
        Filters filters = new Filters();

        if (mRootView != null) {
            filters.setSubject(getSelectedSubject());
            filters.setLevel(getSelectedLevel());
            try {
                filters.setDate(new Timestamp((new SimpleDateFormat("dd MMM yyyy", Locale.CANADA_FRENCH).parse(getSelectedDate()))));
            } catch (Exception e) {
            }
            filters.setSortBy(getSelectedSortBy());
            filters.setSortDirection(getSortDirection());
        }

        return filters;
    }

    public interface FilterListener {
        void onFilter(Filters filters);
    }

    public interface FiltersDialogHandler {
        void addFilters(boolean doCancel);
    }

}
