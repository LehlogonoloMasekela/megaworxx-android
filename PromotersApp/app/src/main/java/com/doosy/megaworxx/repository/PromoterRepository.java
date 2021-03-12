package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.PromoterApiClient;
import com.doosy.megaworxx.api.StockApiClient;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.entity.Stock;
import com.doosy.megaworxx.model.AddStockModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.model.ServerResponse;

import java.util.List;

public class PromoterRepository {
    private final PromoterApiClient promoterApiClient;

    public static PromoterRepository getInstance(){
        return new PromoterRepository();
    }

    private PromoterRepository(){
        promoterApiClient = PromoterApiClient.getInstance();
    }

    public LiveData<DataServerResponse<Promoter>> getDataResponse(){
        return promoterApiClient.getDataResponse();

    }

    public void login(LoginModel model) {
        promoterApiClient.login( model);
    }
}
