package com.doosy.megaworxx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.doosy.megaworxx.entity.Today;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.Setting;
import com.doosy.megaworxx.viewmodel.CampaignViewModel;
import com.doosy.megaworxx.viewmodel.TokenViewModel;

public class ErrorActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout connectionLayout;
    public TextView txtRetry;
    private ProgressBar mProgressBar;
    private Setting mSetting;

    private CampaignViewModel mCampaignViewModel;
    private LiveData<DataServerResponse<Today>> mResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCampaignViewModel = ViewModelProviders.of(this).get(CampaignViewModel.class);

        setContentView(R.layout.activity_error);
        mSetting = new Setting(this);
        initView();

        txtRetry.setOnClickListener(this);
    }

    private void initView(){
        txtRetry = findViewById(R.id.txtRetry);
        connectionLayout = findViewById(R.id.connectionLayout);
        mProgressBar = findViewById(R.id.progress_bar);
    }

    private void resetAll(){
        connectionLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    private void showLoading(){
        connectionLayout.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void showError(){
        connectionLayout.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.txtRetry){
            getConfig();
        }
    }

    private void getConfig() {
        showLoading();
        subscribe();
    }

    int count = 0;

    private void subscribe() {
        mCampaignViewModel.fetchTodayDate(mSetting.getToken());
        mResponse = mCampaignViewModel.getTodayResponse();
        count++;
        mResponse.observe(this, new Observer<DataServerResponse<Today>>() {
            @Override
            public void onChanged(DataServerResponse<Today> response) {

                mResponse.removeObserver(this);
                if(mResponse.hasActiveObservers()){
                    mResponse.removeObservers(ErrorActivity.this);
                }

                if(response == null){
                    showError();
                    return;
                }

                if(response.isSuccessful()){
                    Today today = response.getData();
                    mSetting.setTodayDate(today.getDate());
                    navigateToHome();
                }

            }
        });
    }

    private void navigateToHome(){
        Intent mainIntent = new Intent(this,
                HomeActivity.class);
        startActivity(mainIntent);
        finish();
    }

}
