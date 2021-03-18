package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.model.CampaignForm;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.request.ServiceGenerator;

public class FeedBackViewModel extends ViewModel {

    private  ApiRepository<DataServerResponse<Feedback>> mApiDataRepository;
    private  ApiRepository<ServerResponse> mApiRepository;

    public FeedBackViewModel(){
        mApiDataRepository = new ApiRepository<>();
        mApiRepository = new ApiRepository<>();
    }

    public LiveData<ServerResponse> getResponse(){
        return mApiRepository.getResponse();
    }

    public LiveData<DataServerResponse<Feedback>> getDataResponse(){
        return mApiDataRepository.getDataResponse();
    }

    public void saveFeedback(String token, CampaignForm campaignForm){
        mApiRepository.process(ServiceGenerator.getPromoterApi().saveCampaignForm(token, campaignForm));
    }

    public void fetchFeedBacks(String token, CampaignModel campaignModel){
        mApiDataRepository.processData(
                ServiceGenerator
                .getPromoterApi()
                .fetchFeedBacks(token, campaignModel.getPromoterId(),
                campaignModel.getCampaignId(), campaignModel.getCampaignLocationId()));
    }


}
