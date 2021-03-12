package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.CheckApiClient;
import com.doosy.megaworxx.api.StockApiClient;
import com.doosy.megaworxx.entity.Stock;
import com.doosy.megaworxx.model.AddStockModel;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;

import java.util.List;

public class StockRepository {
    private final StockApiClient mStockApiClient;

    public static StockRepository getInstance(){
        return new StockRepository();
    }

    private StockRepository(){
        mStockApiClient = StockApiClient.getInstance();
    }

    public LiveData<DataServerResponse<Stock>> getDataResponse(){
        return mStockApiClient.getDataResponse();

    } public LiveData<ServerResponse> getResponse(){
        return mStockApiClient.getResponse();
    }

    public void fetchStock(String token,String campaignId){
        mStockApiClient.fetchStock(token, campaignId);
    }


    public void addStock(String token,List<AddStockModel> addStockModel) {
        mStockApiClient.addStock(token, addStockModel);
    }

    public void fetchPromoterStock(String token,String promoterId,String campaignId, String campaignLocationId) {
        mStockApiClient.fetchPromoterStock(token, promoterId, campaignId,  campaignLocationId);
    }
}
