package com.mgl7130.curve.pages.student.ui.search.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mgl7130.curve.common.QueryLiveData;
import com.mgl7130.curve.common.Resource;
import com.mgl7130.curve.models.Cours;
import com.mgl7130.curve.pages.student.ui.search.model.Filters;

import java.util.List;

/**
 * ViewModel for {@link com.mgl7130.curve.pages.student.ui.search.list.StudentSearchRecyclerFragment}.
 */
public class StudentSearchFilterViewModel extends ViewModel {

    private final MutableLiveData<Filters> mFilters = new MutableLiveData<>();
    private final LiveData<Resource<List<Cours>>> mClasses;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();


    public StudentSearchFilterViewModel() {
        mFilters.setValue(Filters.getDefault());
        mClasses = Transformations.switchMap(mFilters, this::classes);
    }

    private QueryLiveData<Cours> classes(@NonNull final Filters filters) {
        return new QueryLiveData<>(toQuery(filters), Cours.class);
    }

    private Query toQuery(final Filters filters) {
        // Construct query basic query
        Query query = mFirestore.collection("classes").whereEqualTo("hasStudent", false);

        // Date (equality filter)
        if (filters.hasDate()) {
            query = query.whereEqualTo(Cours.FIELD_DATE, filters.getDate());
        }

        // Level (equality filter)
        if (filters.hasLevel()) {
            query = query.whereEqualTo(Cours.FIELD_LEVEL, filters.getLevel());
        }

        // Subject (equality filter)
        if (filters.hasSubject()) {
            query = query.whereEqualTo(Cours.FIELD_SUBJECT, filters.getSubject());
        }

        // Sort by (orderBy with direction)
        if (filters.hasSortBy()) {
            query = query.orderBy(filters.getSortBy(), filters.getSortDirection());
        }

        /* query could be limited like: query.limit(5) */
        return query;
    }

    public void setFilters(final Filters filters) {
        this.mFilters.setValue(filters);
    }

    public LiveData<Resource<List<Cours>>> getClasses() {
        return mClasses;
    }

}
