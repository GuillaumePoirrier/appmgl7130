package com.mgl7130.curve.pages.student.ui.classes.list;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.mgl7130.curve.R;
import com.mgl7130.curve.adapter.FirestoreAdapter;
import com.mgl7130.curve.models.Cours;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentClassAdapter extends FirestoreAdapter<StudentClassAdapter.ViewHolder> {

    public interface OnClassSelectedListener {
        void onClassSelected(DocumentSnapshot restaurant);
    }

    private OnClassSelectedListener mListener;

    public StudentClassAdapter(Query query, OnClassSelectedListener listener){
        super(query);
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.student_item_class, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_tv_subject)
        TextView subject;

        @BindView(R.id.item_tv_level)
        TextView level;

        @BindView(R.id.item_tv_from)
        TextView startTime;

        @BindView(R.id.item_tv_to)
        TextView endTime;

        @BindView(R.id.item_tv_date_day)
        TextView dateDay;

        @BindView(R.id.item_tv_date_month)
        TextView dateMonth;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnClassSelectedListener listener) {

            Cours cours = snapshot.toObject(Cours.class);
            Resources resources = itemView.getResources();

            subject.setText(cours.getSubject().toString());
            level.setText(cours.getLevel().toString());
            startTime.setText((new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(cours.getStartDate().toDate())));
            endTime.setText((new SimpleDateFormat("HH:mm", Locale.CANADA_FRENCH).format(cours.getEndDate().toDate())));
            dateDay.setText((new SimpleDateFormat("dd", Locale.CANADA_FRENCH).format(cours.getDate().toDate())));
            dateMonth.setText((new SimpleDateFormat("MMM", Locale.CANADA_FRENCH).format(cours.getDate().toDate())));


            //Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("click");
                    if (listener != null) {
                        listener.onClassSelected(snapshot);
                    }
                }
            });
        }

    }


    
}