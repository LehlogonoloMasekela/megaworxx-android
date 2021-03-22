package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.FirebaseModel;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.model.StatusModel;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.repository.StockItemRepository;
import com.doosy.megaworxx.request.ServiceGenerator;

public class HomeViewModel extends ViewModel {

    private ApiRepository<ServerResponse> mApiDataRepository;

    public HomeViewModel() {
        mApiDataRepository = new ApiRepository<>();
    }

    public LiveData<ServerResponse> getResponse(){
        return mApiDataRepository.getResponse();
    }

    public void saveFirBaseToken(String token, FirebaseModel model){
        mApiDataRepository.process(ServiceGenerator.getPromoterApi().saveFireBaseToken(token, model));
    }

}
