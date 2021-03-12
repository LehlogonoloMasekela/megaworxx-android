package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.Sales;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.PromoterSaleModel;
import com.doosy.megaworxx.repository.SalesRepository;
import com.doosy.megaworxx.repository.StockRepository;

public class SaleViewModel extends ViewModel {

    private SalesRepository salesRepository;


    public SaleViewModel(){
        salesRepository = SalesRepository.getInstance();
    }

    public LiveData<DataServerResponse<Sales>> getDataResponse(){
        return salesRepository.getDataResponse();
    }

    public void fetchSales(String token,PromoterSaleModel promoterSaleModel){
        salesRepository.fetchSale(token, promoterSaleModel);
    }

    public void fetchPromoterSale(String token,String promoterId,String campaignId, String campaignLocationId) {
        salesRepository.fetchPromoterSale(token, promoterId, campaignId,  campaignLocationId);
    }

}
