package com.doosy.megaworxx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.doosy.megaworxx.entity.Today;
import com.doosy.megaworxx.model.ClientToken;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.ui.home.message.MessageActivity;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.viewmodel.CampaignViewModel;
import com.doosy.megaworxx.viewmodel.TokenViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private CampaignViewModel mCampaignViewModel;
    private LiveData<DataServerResponse<ClientToken>> mTokenResponse;
    private LiveData<DataServerResponse<Today>> mResponse;

    private Bundle bundleNotification = null;

    private TokenViewModel mTokenViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCampaignViewModel = new ViewModelProvider(this).get(CampaignViewModel.class);
        mTokenViewModel = new ViewModelProvider(this).get(TokenViewModel.class);
        hideSystemUi();
        setContentView(R.layout.activity_splash);

        if(!settings.isSubscribedToFirebase()){
            Log.d(Constants.TAG, "Subscribe to topic");
            subscribeToTopic();
        }

        if(getIntent().hasExtra("id")){
            bundleNotification = new Bundle();
            Bundle bundle = getIntent().getExtras();
            String id = bundle.getString("id");
            Log.d(Constants.TAG, "ID here: "+id);
            bundleNotification.putString("id", id);
            bundleNotification.putBoolean("canOpenHome", true);
        }

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

    @Override
    public void displayPage(boolean hasContent) {

    }

    @Override
    public void retryLoad() {

    }

    private void subscribeToTopic(){
        String topic = getString(R.string.firebase_main_topic);
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successfully subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Failed to subscribe.";
                            settings.setSubscribeToFirebase(false);
                        }
                        settings.setSubscribeToFirebase(true);
                        Log.d(Constants.TAG, "Status: "+msg);
                    }
                });
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
                  navigateToError();
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

        if(bundleNotification != null && settings.isLoggedIn()){
            mainIntent = new Intent(SplashActivity.this,
                    MessageActivity.class);
            mainIntent.putExtras(bundleNotification);
        }else if(settings.isLoggedIn()){
            mainIntent = new Intent(SplashActivity.this,
                    HomeActivity.class);
        }

        startActivity(mainIntent);
        finish();
    }

    private void navigateToError(){
        Intent mainIntent = new Intent(SplashActivity.this,
                ErrorActivity.class);

        if(bundleNotification != null){
            mainIntent.putExtras(bundleNotification);
        }

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
                    navigateToError();
                    return;
                }

                if(response.isSuccessful()){
                    Today today = response.getData();

                    settings.setTodayDate(today.getDate());
                    navigateToNextScreen();
                    return;
                }

                navigateToError();
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
