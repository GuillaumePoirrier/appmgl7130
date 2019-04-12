package com.mgl7130.curve.common;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.mgl7130.curve.R;
import com.mgl7130.curve.models.Subject;

public final class BindingAdapters {
    private BindingAdapters() {
    }

    @BindingAdapter("showView")
    public static void showView(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("hideView")
    public static void hideView(View view, boolean hide) {
        showView(view, !hide);
    }

    @BindingAdapter("subjectImage")
    public static void subjectImage(ImageView view, Subject subject) {

        if (subject != null) {
            switch (subject.toString()) {
                case "Mathematiques": {
                    view.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.logo_math));
                    break;
                }
                case "Physique": {
                    view.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.logo_physic));
                    break;
                }
                case "Chimie": {
                    view.setImageDrawable(ContextCompat.getDrawable(view.getContext(), R.drawable.logo_chemistry));
                    break;
                }
            }
        }
    }

    @BindingAdapter("error")
    public static void setError(EditText editText, Object strOrResId) {
        if (strOrResId instanceof Integer) {
            editText.setError(
                    editText.getContext().getString((Integer) strOrResId));
        } else {
            editText.setError((String) strOrResId);
        }
    }

    @BindingAdapter("onFocus")
    public static void bindFocusChange(EditText editText, View.OnFocusChangeListener onFocusChangeListener) {
        if (editText.getOnFocusChangeListener() == null) {
            editText.setOnFocusChangeListener(onFocusChangeListener);
        }
    }

}