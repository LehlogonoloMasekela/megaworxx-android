package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.PromoterApiClient;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;

public class PromoterRepository {
    private static PromoterRepository instance;
    private final PromoterApiClient mPromoterApiClient;

    public static PromoterRepository getInstance(){
        if(instance == null){
            instance = new PromoterRepository();
        }

        return  instance;
    }

    private PromoterRepository(){
        mPromoterApiClient = PromoterApiClient.getInstance();
    }

    public LiveData<DataServerResponse<Promoter>> getLoginResponse(){
        return mPromoterApiClient.getLoginResponse();
    }

    public void login(LoginModel loginModel){
        mPromoterApiClient.login(loginModel);
    }


}
