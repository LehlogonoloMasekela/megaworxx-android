package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.StockApiClient;
import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.model.AddStockModel;
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

    public LiveData<DataServerResponse<StockSaleBase>> getCampaignStock(){
        return mStockApiClient.getCampaignStock();
    }

    public LiveData<DataServerResponse<StockSaleBase>> getCampaignPromoterStock(){
        return mStockApiClient.getCampaignPromoterStock();
    }

    public LiveData<ServerResponse> getResponse(){
        return mStockApiClient.getResponse();
    }

    public void fetchCampaignStock(String token, String campaignId){
        mStockApiClient.fetchCampaignStock(token, campaignId);
    }

    public void addStock(String token,List<AddStockModel> addStockModel) {
        mStockApiClient.addStock(token, addStockModel);
    }

    public void fetchCampaignPromoterStock(String token, String promoterId, String campaignId, String campaignLocationId) {
        mStockApiClient.fetchCampaignPromoterStock(token, promoterId, campaignId,  campaignLocationId);
    }
}
