package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.SaleApiClient;
import com.doosy.megaworxx.api.StockApiClient;
import com.doosy.megaworxx.entity.Sales;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.PromoterSaleModel;

public class SalesRepository {
    private final SaleApiClient saleApiClient;

    public static SalesRepository getInstance(){
        return new SalesRepository();
    }

    private SalesRepository(){
        saleApiClient = SaleApiClient.getInstance();
    }

    public LiveData<DataServerResponse<Sales>> getDataResponse(){
        return saleApiClient.getDataResponse();
    }

    public void fetchSale(String token,PromoterSaleModel promoterSaleModel){
        saleApiClient.fetchSale(token, promoterSaleModel);
    }

    public void fetchPromoterSale(String token,String promoterId,String campaignId, String campaignLocationId) {
        saleApiClient.fetchPromoterSale(token, promoterId, campaignId,  campaignLocationId);
    }


}
