package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.SaleApiClient;
import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.PromoterSaleModel;
import com.doosy.megaworxx.model.SaleModel;
import com.doosy.megaworxx.model.ServerResponse;

public class SalesRepository {
    private final SaleApiClient saleApiClient;

    public static SalesRepository getInstance(){
        return new SalesRepository();
    }

    private SalesRepository(){
        saleApiClient = SaleApiClient.getInstance();
    }

    public LiveData<DataServerResponse<StockSaleBase>> getDataResponse(){
        return saleApiClient.getSaleResponse();
    }

    public void saveSale(String token, SaleModel saleModel){
        saleApiClient.saveSale(token, saleModel);
    }

    public LiveData<ServerResponse> getResponse(){
        return saleApiClient.getResponse();
    }

    public void fetchSale(String token,PromoterSaleModel promoterSaleModel){
        saleApiClient.fetchSale(token, promoterSaleModel);
    }

    public void fetchPromoterSale(String token,String promoterId,String campaignId, String campaignLocationId) {
        saleApiClient.fetchPromoterSale(token, promoterId, campaignId,  campaignLocationId);
    }


}
