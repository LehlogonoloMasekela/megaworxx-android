package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.StockApiClient;
import com.doosy.megaworxx.api.StockItemApiClient;
import com.doosy.megaworxx.entity.Stock;
import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.model.AddStockModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;

import java.util.List;

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

    public void fetchItems(String token){
        mStockApiClient.fetchItems(token);
    }

}
