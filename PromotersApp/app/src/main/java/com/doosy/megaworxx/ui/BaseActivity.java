package com.doosy.megaworxx.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
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
    private LinearLayout connectionLayout;

    protected Setting settings;
    protected TokenTimer mTokenTimer;
    private TextView txtReload;
    public TextView noContentCaption;

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

    public abstract void displayPage(boolean hasContent);

    public abstract void retryLoad();

    public void displayErrorPage(){
        resetAll();
        noContentCaption.setText(getString(R.string.general_error_message));
        frameLayout.setVisibility(View.GONE);
        connectionLayout.setVisibility(View.VISIBLE);
        txtReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                retryLoad();
            }
        });
    }

    private void initViews(){
        successLayout = constraintLayout.findViewById(R.id.successLayout);
        tvSuccessMessage = constraintLayout.findViewById(R.id.tvSuccessMessage);
        errorLayout = constraintLayout.findViewById(R.id.errorLayout);
        tvErrorMessage = constraintLayout.findViewById(R.id.tvErrorMessage);
        connectionLayout = constraintLayout.findViewById(R.id.connectionLayout);
        txtReload = constraintLayout.findViewById(R.id.txtRetry);
        noContentCaption = constraintLayout.findViewById(R.id.tvConnectionMessage);
    }

    private void resetAll(){
        connectionLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        successLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
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
