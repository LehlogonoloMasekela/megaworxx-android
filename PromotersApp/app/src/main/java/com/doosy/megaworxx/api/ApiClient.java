package com.doosy.megaworxx.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doosy.megaworxx.AppExecutors;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.util.Constants;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class ApiClient<T> {
    private Call<T> mCall;
    private MutableLiveData<DataServerResponse<T>> mResponse;
    private ApiRunnable mApiRunnable;

    public ApiClient(Call<T> call){
        mResponse = new MutableLiveData<>();
        mCall = call;
    }

    public LiveData<DataServerResponse<T>> getResponse(){
        return mResponse;
    }

    public void process(){
        if(mApiRunnable != null){
            mApiRunnable = null;
        }

        mApiRunnable = new ApiRunnable();

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mApiRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class ApiRunnable implements Runnable {

        private boolean cancelRequest = false;

        public ApiRunnable(){
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            try{

                Response response = apiCall().execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){
                    Log.d(Constants.TAG,"Inside 200");
                    T clientToken = (T) response.body();

                    if(clientToken != null){

                        DataServerResponse<T> serverResponse = new DataServerResponse<>();
                        serverResponse.setSuccessful(true);
                        serverResponse.setData(clientToken);
                        mResponse.postValue(serverResponse);

                        return;
                    }

                }
                mResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mResponse.postValue(null);
            }

        }
    }

    private Call<T> apiCall(){
        //Call<ClientToken> apiCall = ServiceGenerator.getCampaignApiToken().getToken(type, scope);
        return (Call<T>) mCall;
    }

}
