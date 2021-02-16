package com.doosy.megaworxx.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doosy.megaworxx.AppExecutors;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.request.ServiceGenerator;
import com.doosy.megaworxx.util.Constants;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class PromoterApiClient {

    private static PromoterApiClient instance;
    private MutableLiveData<DataServerResponse<Promoter>> mLoginResponse;
    private LoginRunnable mLoginRunnable;

    public static PromoterApiClient getInstance(){
        if(instance == null){
            instance = new PromoterApiClient();
        }
        return instance;
    }

    private PromoterApiClient(){
        mLoginResponse = new MutableLiveData<>();
    }

    public LiveData<DataServerResponse<Promoter>> getLoginResponse(){
        return mLoginResponse;
    }


    public void login(LoginModel loginModel){
        if(mLoginRunnable != null){
            mLoginRunnable = null;
        }

        mLoginRunnable = new LoginRunnable( loginModel);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mLoginRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class LoginRunnable implements Runnable {

        LoginModel mLoginModel;

        private boolean cancelRequest = false;

        public LoginRunnable(LoginModel loginModel){
            this.cancelRequest = false;
            this.mLoginModel = loginModel;
        }

        @Override
        public void run() {

            try{

                Response response = loginCall(mLoginModel).execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){

                    DataServerResponse<Promoter> serverResponse = ((DataServerResponse<Promoter>)(response.body()));

                    if(serverResponse != null && serverResponse.isSuccessful()){
                        Log.d(Constants.TAG,"FirstName Data: "+serverResponse.getData().getFirstName());
                        Log.d(Constants.TAG,"LastName Data: "+serverResponse.getData().getLastName());
                        mLoginResponse.postValue(serverResponse);
                        return;
                    }

                }
                Log.d(Constants.TAG,"After 200 if status");
                Log.d(Constants.TAG,"Server status: "+response.body());

                mLoginResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mLoginResponse.postValue(null);
            }

        }
    }

    private Call<DataServerResponse<Promoter>> loginCall(LoginModel loginModel){
        return ServiceGenerator.getPromoterApi().login(loginModel);
    }

}
