package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.model.AddStockModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.repository.StockRepository;

import java.util.List;

public class StockViewModel extends ViewModel {

    private StockRepository stockRepository;
 
    public StockViewModel(){
        stockRepository = StockRepository.getInstance();
    }


    public LiveData<DataServerResponse<StockSaleBase>> getCampaignStock(){
        return stockRepository.getCampaignStock();
    }

    public LiveData<DataServerResponse<StockSaleBase>> getCampaignPromoterStocks(){
        return stockRepository.getCampaignPromoterStock();
    }

    public LiveData<ServerResponse> getResponse(){
        return stockRepository.getResponse();
    }

    public void fetchCampaignStock(String token, String campaignId){
        stockRepository.fetchCampaignStock(token, campaignId);
    }

    public void addStock(String token,List<AddStockModel> addStockModel){
        stockRepository.addStock(token, addStockModel);
    }

    public void fetchCampaignPromoterStock(String token, String promoterId, String campaignId, String campaignLocationId) {
        stockRepository.fetchCampaignPromoterStock(token, promoterId, campaignId,  campaignLocationId);
    }


}
