package com.doosy.megaworxx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.doosy.megaworxx.entity.Today;
import com.doosy.megaworxx.model.ClientToken;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.TodayCampaignModel;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.ui.home.HomeFragment;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.Util;
import com.doosy.megaworxx.viewmodel.CampaignViewModel;
import com.doosy.megaworxx.viewmodel.TokenViewModel;

public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private CampaignViewModel mCampaignViewModel;
    private LiveData<DataServerResponse<ClientToken>> mTokenResponse;
    private LiveData<DataServerResponse<Today>> mResponse;

    private TokenViewModel mTokenViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCampaignViewModel = ViewModelProviders.of(this).get(CampaignViewModel.class);
        mTokenViewModel = ViewModelProviders.of(this).get(TokenViewModel.class);
        hideSystemUi();
        setContentView(R.layout.activity_splash);

        if(!settings.isOnline()){
            navigateToError();
            return;
        }

        if(mTokenTimer.isTokenAlive()){
            getConfig();
        }else{
            getToken();
        }

    }

    private void getToken(){

        mTokenViewModel.processApi();
        mTokenResponse = mTokenViewModel.getResponse();

        mTokenResponse.observe(this, new Observer<DataServerResponse<ClientToken>>() {
            @Override
            public void onChanged(DataServerResponse<ClientToken> response) {
                if(response != null && response.getData() != null){
                    //Store token and update time to expire
                    String token = response.getData().getAccessToken();
                    settings.setToken(token);
                    mTokenTimer.setTime(2);
                    getConfig();

                }else{
                    //Implement messages when the token was not retrieved
                    // Log.d(Constants.TAG, "A huna token yo waniwaho.");
                }
            }
        });
    }



    private void getConfig(){
        mCampaignViewModel.fetchTodayDate(settings.getToken());
        subscribe();
    }

    private void navigateToNextScreen(){
        Intent mainIntent = new Intent(SplashActivity.this,
                LoginActivity.class);

        if(settings.isLoggedIn()){
            mainIntent = new Intent(SplashActivity.this,
                    HomeActivity.class);
        }
        startActivity(mainIntent);
        finish();
    }

    private void navigateToError(){
        Intent mainIntent = new Intent(SplashActivity.this,
                ErrorActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void subscribe() {
        mCampaignViewModel.fetchTodayDate(settings.getToken());
        mResponse = mCampaignViewModel.getTodayResponse();

        mResponse.observe(this, new Observer<DataServerResponse<Today>>() {
            @Override
            public void onChanged(DataServerResponse<Today> response) {

                mResponse.removeObserver(this);
                if(mResponse.hasActiveObservers()){
                    mResponse.removeObservers(SplashActivity.this);
                }

                if(response == null){
                    Log.d(Constants.TAG, "No date:");
                    return;
                }

                if(response.isSuccessful()){
                    Today today = response.getData();

                    settings.setTodayDate(today.getDate());
                    navigateToNextScreen();
                }

            }
        });
    }

    @SuppressLint("InlineApi")
    private void hideSystemUi() {
        /*getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);*/

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

}
