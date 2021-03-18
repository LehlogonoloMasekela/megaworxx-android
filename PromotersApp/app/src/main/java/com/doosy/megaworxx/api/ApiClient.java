package com.doosy.megaworxx.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doosy.megaworxx.AppExecutors;
import com.doosy.megaworxx.model.ClientToken;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.util.Constants;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class ApiClient<T> {
    private Call<T> mCall;
    private MutableLiveData<T> mDataResponse;
    private MutableLiveData<ServerResponse> mResponse;
    private MutableLiveData<DataServerResponse<ClientToken>> mTokenResponse;
    private ApiDataRunnable mApiDataRunnable;
    private ApiRunnable mApiRunnable;
    private ApiTokenRunnable mApiTokenRunnable;

    public ApiClient(Call<T> call){
        mDataResponse = new MutableLiveData<>();
        mResponse = new MutableLiveData<>();
        mTokenResponse = new MutableLiveData<>();
        mCall = call;
    }

    public ApiClient(){
        mDataResponse = new MutableLiveData<>();
        mResponse = new MutableLiveData<>();
        mTokenResponse = new MutableLiveData<>();
        mCall = null;
    }

    public LiveData<T> getDataResponse(){
        return mDataResponse;
    }

    public LiveData<ServerResponse> getResponse(){
        return mResponse;
    }

    public LiveData<DataServerResponse<ClientToken>> getTokenResponse(){
        return mTokenResponse;
    }

    public void processToken(){
        if(mApiTokenRunnable != null){
            mApiTokenRunnable = null;
        }

        mApiTokenRunnable = new ApiTokenRunnable();

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mApiTokenRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    public void processData(){
        if(mApiDataRunnable != null){
            mApiDataRunnable = null;
        }

        mApiDataRunnable = new ApiDataRunnable();

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mApiDataRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    public void processData(Call<T> callBack){
        mCall = callBack;

        if(mApiDataRunnable != null){
            mApiDataRunnable = null;
        }

        mApiDataRunnable = new ApiDataRunnable();

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mApiDataRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
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

    public void process(Call<T> callBack){
        mCall = callBack;

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

    private class ApiDataRunnable implements Runnable {

        private boolean cancelRequest = false;

        public ApiDataRunnable(){
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            try{

                Response<T> response = apiCall().execute();

                Log.d(Constants.TAG,response.body().toString());
                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){
                    Log.d(Constants.TAG,"About to throw data");
                    Log.d(Constants.TAG,response.body().toString());
                    T data = response.body();

                    if(data != null){

                        mDataResponse.postValue(data);

                        return;
                    }

                }
                mDataResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mDataResponse.postValue(null);
            }

        }
    }

    private class ApiTokenRunnable implements Runnable {

        private boolean cancelRequest = false;

        public ApiTokenRunnable(){
            this.cancelRequest = false;
        }

        @Override
        public void run() {

            try{

                Response<T> response = apiCall().execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){
                    Log.d(Constants.TAG,"Inside 200");
                    ClientToken data = (ClientToken) response.body();

                    if(data != null){

                        DataServerResponse<ClientToken> serverResponse = new DataServerResponse<>();
                        serverResponse.setSuccessful(true);
                        serverResponse.setData(data);
                        mTokenResponse.postValue(serverResponse);

                        return;
                    }

                }
                mTokenResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mTokenResponse.postValue(null);
            }

        }
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
                    ServerResponse data = (ServerResponse) response.body();

                    if(data != null){

                        mResponse.postValue(data);

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
