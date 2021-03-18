package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Message;
import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.StatusModel;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.repository.StockItemRepository;
import com.doosy.megaworxx.request.ServiceGenerator;

public class CheckViewModel extends ViewModel {

    private ApiRepository<DataServerResponse<StatusModel>> mApiDataRepository;

    public CheckViewModel() {
        mApiDataRepository = new ApiRepository<>();
    }

    public LiveData<DataServerResponse<StatusModel>> getResponse(){
        return mApiDataRepository.getDataResponse();
    }

    public void getStatus(String token, CampaignModel model){
        mApiDataRepository.processData(ServiceGenerator.getPromoterApi().getStatus(token,
                model.getPromoterId(), model.getCampaignId(),
                model.getCampaignLocationId(), model.getCampaignDateId()));
    }


}
