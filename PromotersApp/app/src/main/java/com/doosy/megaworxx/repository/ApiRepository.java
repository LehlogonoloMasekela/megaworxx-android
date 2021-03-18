package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.ApiClient;
import com.doosy.megaworxx.model.ClientToken;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;

import retrofit2.Call;

public class ApiRepository<T> {

    private ApiClient<T> mApiClient;

    public ApiRepository(Call<T> callBack) {
        mApiClient = new ApiClient<T>(callBack);
    }

    public ApiRepository(){
        mApiClient = new ApiClient<>();
    }

    public LiveData<T> run(Call<T> callBack){
        return getDataResponse();
    }

    public LiveData<T> getDataResponse(){
       return mApiClient.getDataResponse();
   }

   public LiveData<ServerResponse> getResponse(){
       return mApiClient.getResponse();
   }

  public LiveData<DataServerResponse<ClientToken>> getTokenResponse(){
       return mApiClient.getTokenResponse();
   }

   public void processData(){
       mApiClient.processData();
   }

   public void processToken(){
       mApiClient.processToken();
   }

    public void processData(Call<T> callBack){
        mApiClient.processData(callBack);
    }

    public void process(){
       mApiClient.process();
   }

    public void process(Call<T> callBack){
        mApiClient.process(callBack);
    }
}
