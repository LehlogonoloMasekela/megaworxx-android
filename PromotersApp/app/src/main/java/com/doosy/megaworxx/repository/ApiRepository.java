package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.ApiClient;
import com.doosy.megaworxx.model.ClientToken;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.request.ServiceGenerator;

import retrofit2.Call;

public class ApiRepository<T> {

    private ApiClient<T> mApiClient;

    public ApiRepository(Call<T> callBack) {
        mApiClient = new ApiClient<T>(callBack);
    }

    public LiveData<DataServerResponse<T>> getResponse(){
       return mApiClient.getResponse();
   }

   public void process(){
       mApiClient.process();
   }

}
