package com.mgl7130.curve.pages.auth;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBinderMapper;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mgl7130.curve.R;
import com.mgl7130.curve.databinding.ActivitySignInBinding;
import com.mgl7130.curve.pages.auth.models.SignInData;
import com.mgl7130.curve.pages.auth.viewmodels.SignInViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        //Get shared preferences to save STAY_Logged or not
        SharedPreferences mSharedPreferences = this.getSharedPreferences(getString(R.string.auth_preferences_file), Context.MODE_PRIVATE);

        SignInViewModel viewModel = new SignInViewModel(mSharedPreferences, mContext);
        ActivitySignInBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        binding.setViewmodel(viewModel);

        viewModel.startActivity.observe(this, new Observer<Class<ProfileChoiceActivity>>() {
            @Override
            public void onChanged(@Nullable Class<ProfileChoiceActivity> profileChoiceActivityClass) {
                Intent intent = new Intent(mContext, profileChoiceActivityClass);
                startActivity(intent);
                finish();
            }
        });

    }
}
