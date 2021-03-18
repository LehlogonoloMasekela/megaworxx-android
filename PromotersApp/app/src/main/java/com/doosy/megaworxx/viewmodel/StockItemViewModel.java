package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.request.ServiceGenerator;

public class StockItemViewModel extends ViewModel {
    private ApiRepository<DataServerResponse<StockSaleBase>> stockResponse;
    //private ApiRepository<DataServerResponse<Sales>> saleResponse;

    public StockItemViewModel() {
        stockResponse = new ApiRepository<>();
        //saleResponse = new ApiRepository<>();
    }

    public LiveData<DataServerResponse<StockSaleBase>> getStockResponse(){
        return stockResponse.getDataResponse();
    }


    public void fetchPromoterStock(String token, String id){
        stockResponse.processData(ServiceGenerator.getPromoterApi().fetchStock(token, id));
    }

    public void fetchPromoterSale(String token, String id){
        stockResponse.processData(ServiceGenerator.getPromoterApi().fetchSale(token, id));
    }

}