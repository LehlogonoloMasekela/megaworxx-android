package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.Stock;
import com.doosy.megaworxx.model.AddStockModel;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.repository.StockRepository;

import java.util.List;

public class StockViewModel extends ViewModel {

    private StockRepository stockRepository;


    public StockViewModel(){
        stockRepository = StockRepository.getInstance();
    }

    public LiveData<DataServerResponse<Stock>> getDataResponse(){
        return stockRepository.getDataResponse();
    }

    public LiveData<ServerResponse> getResponse(){
        return stockRepository.getResponse();
    }

    public void fetchStock(String token,String campaignId){
        stockRepository.fetchStock(token, campaignId);
    }
    public void addStock(String token,List<AddStockModel> addStockModel){
        stockRepository.addStock(token, addStockModel);
    }

    public void fetchPromoterStock(String token,String promoterId,String campaignId, String campaignLocationId) {
        stockRepository.fetchPromoterStock(token, promoterId, campaignId,  campaignLocationId);
    }

}
