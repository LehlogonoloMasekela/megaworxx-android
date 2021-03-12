package com.doosy.megaworxx.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.Setting;
import com.doosy.megaworxx.util.TokenTimer;

public abstract class BaseActivity  extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private FrameLayout frameLayout;
    private ConstraintLayout constraintLayout;

    private LinearLayout successLayout;
    private TextView tvSuccessMessage;
    private LinearLayout errorLayout;
    private TextView tvErrorMessage;

    protected Setting settings;
    protected TokenTimer mTokenTimer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        settings = new Setting(this);
        mTokenTimer = new TokenTimer(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(int layoutResID) {
        constraintLayout = (ConstraintLayout)getLayoutInflater()
                .inflate(R.layout.activity_base, null);
        frameLayout = constraintLayout.findViewById(R.id.activity_content);
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(constraintLayout);
        initViews();
    }

    private void initViews(){
        successLayout = constraintLayout.findViewById(R.id.successLayout);
        tvSuccessMessage = constraintLayout.findViewById(R.id.tvSuccessMessage);
        errorLayout = constraintLayout.findViewById(R.id.errorLayout);
        tvErrorMessage = constraintLayout.findViewById(R.id.tvErrorMessage);
    }

    private void resetAll(){
        errorLayout.setVisibility(View.GONE);
        successLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }

    public void showNoInternet(){
        resetAll();
        frameLayout.setVisibility(View.GONE);
    }

    public void showError(String errorMsg){
        resetAll();
        tvErrorMessage.setText(errorMsg);
        errorLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                errorLayout.setVisibility(View.GONE);
            }
        }, Constants.MESSAGE_TIMEOUT);

    }

    public void showSuccess(String successMessage){
        resetAll();
        tvSuccessMessage.setText(successMessage);
        successLayout.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                successLayout.setVisibility(View.GONE);
            }
        }, Constants.MESSAGE_TIMEOUT);
    }

    public void showLoading(){
        resetAll();
        mProgressBar.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
    }

    public void hideLoading(){
        resetAll();
        mProgressBar.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
    }

    public void showToast(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean isConnected(){
        return  settings.getConnectionType() != Setting.ConnectionType.None;
    }

}
