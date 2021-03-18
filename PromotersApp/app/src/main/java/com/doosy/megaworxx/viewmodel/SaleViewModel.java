package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.PromoterSaleModel;
import com.doosy.megaworxx.model.SaleModel;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.repository.SalesRepository;

public class SaleViewModel extends ViewModel {

    private SalesRepository salesRepository;

    public SaleViewModel(){
        salesRepository = SalesRepository.getInstance();
    }

    public LiveData<DataServerResponse<StockSaleBase>> getDataResponse(){
        return salesRepository.getDataResponse();
    }
    public LiveData<ServerResponse> getResponse(){
        return salesRepository.getResponse();
    }

    public void saveCampaignSale(String token, SaleModel model){

        salesRepository.saveSale(token, model);
    }

    public void fetchSales(String token,PromoterSaleModel promoterSaleModel){
        salesRepository.fetchSale(token, promoterSaleModel);
    }

    public void fetchPromoterSale(String token,String promoterId,String campaignId, String campaignLocationId) {
        salesRepository.fetchPromoterSale(token, promoterId, campaignId,  campaignLocationId);
    }

}
