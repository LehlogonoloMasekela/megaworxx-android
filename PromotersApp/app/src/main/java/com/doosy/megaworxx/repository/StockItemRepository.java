package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.StockItemApiClient;
import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;

public class StockItemRepository {
    private final StockItemApiClient mStockApiClient;

    public static StockItemRepository getInstance(){
        return new StockItemRepository();
    }

    private StockItemRepository(){
        mStockApiClient = StockItemApiClient.getInstance();
    }

    public LiveData<DataServerResponse<StockItem>> getDataResponse(){
        return mStockApiClient.getDataResponse();

    } public LiveData<ServerResponse> getResponse(){
        return mStockApiClient.getResponse();
    }

    public void fetchStockItems(String token){
        mStockApiClient.fetchStockItems(token);
    }

}
