package com.doosy.megaworxx.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doosy.megaworxx.AppExecutors;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.request.ServiceGenerator;
import com.doosy.megaworxx.util.Constants;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class CheckApiClient {

    private MutableLiveData<ServerResponse> mCheckInOutResponse;
    private CheckRunnable mCheckInOutRunnable;

    public static CheckApiClient getInstance(){

        return new CheckApiClient();
    }

    private CheckApiClient(){
        mCheckInOutResponse = new MutableLiveData<>();
    }

    public LiveData<ServerResponse> getCheckResponse(){
        return mCheckInOutResponse;
    }

    public void checkInOut(String token,CheckModel checkModel){
        if(mCheckInOutRunnable != null){
            mCheckInOutRunnable = null;
        }

        mCheckInOutRunnable = new CheckRunnable(token, checkModel);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mCheckInOutRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }


    private class CheckRunnable implements Runnable {

        CheckModel mCheckModel;
        String token;

        private boolean cancelRequest = false;

        public CheckRunnable(String token, CheckModel checkModel){
            this.cancelRequest = false;
            this.mCheckModel = checkModel;
            this.token = token;
        }

        @Override
        public void run() {

            try{

                Response response = checkInOutCall(token, mCheckModel).execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){

                    ServerResponse serverResponse = ((ServerResponse)(response.body()));

                    if(serverResponse != null){
                        mCheckInOutResponse.postValue(serverResponse);
                        Log.d(Constants.TAG,"Model: "+mCheckModel);
                        return;
                    }

                }

                mCheckInOutResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mCheckInOutResponse.postValue(null);
            }

        }
    }


    private Call<ServerResponse> checkInOutCall(String token, CheckModel checkModel){
        return ServiceGenerator.getPromoterApi().checkInOut(token, checkModel);
    }

}
