package com.doosy.megaworxx.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doosy.megaworxx.AppExecutors;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.request.ServiceGenerator;
import com.doosy.megaworxx.util.Constants;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class PromoterApiClient {
    private static PromoterApiClient instance;

    private MutableLiveData<DataServerResponse<Promoter>> dataResponse;
    private PromoterRunnable mPromoterRunnable;


    public static PromoterApiClient getInstance(){
        return new PromoterApiClient();
    }

    private PromoterApiClient(){
        dataResponse = new MutableLiveData<>();
    }

    public LiveData<DataServerResponse<Promoter>> getDataResponse(){
        return dataResponse;
    }

    public void login(LoginModel loginModel){
        dataResponse = new MutableLiveData<>();
        if(mPromoterRunnable != null){
            mPromoterRunnable = null;
        }

        mPromoterRunnable = new PromoterRunnable(loginModel);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mPromoterRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }


    private class PromoterRunnable implements Runnable {
      LoginModel model;

        private boolean cancelRequest = false;

        public PromoterRunnable(LoginModel model){
            this.cancelRequest = false;
            this.model = model;
        }

        @Override
        public void run() {

            try{

                Response<DataServerResponse<Promoter>> response = loginCall(model).execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){

                    DataServerResponse<Promoter> promoterResponse = response.body();

                    if(promoterResponse != null && promoterResponse.isSuccessful()){
                        dataResponse.postValue(promoterResponse);

                    }else{
                        dataResponse.postValue(promoterResponse);
                    }

                    return;
                }

                dataResponse.postValue(null);

            }catch (Exception e){

                dataResponse.postValue(null);
            }

        }
    }

    private Call<DataServerResponse<Promoter>> loginCall(LoginModel model){
        return ServiceGenerator.getPromoterApiAuth().login(model);
    }


}
