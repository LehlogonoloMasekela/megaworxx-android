package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.repository.StockItemRepository;

public class ItemViewModel extends ViewModel {

    private StockItemRepository stockRepository;


    public ItemViewModel(){
        stockRepository = StockItemRepository.getInstance();
    }

    public LiveData<DataServerResponse<StockItem>> getDataResponse(){
        return stockRepository.getDataResponse();
    }

    public void fetchStockItems(String token){
        stockRepository.fetchStockItems(token);
    }

}
