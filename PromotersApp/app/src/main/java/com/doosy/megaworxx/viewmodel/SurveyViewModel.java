package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.entity.Survey;
import com.doosy.megaworxx.model.CampaignForm;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.request.ServiceGenerator;

public class SurveyViewModel extends ViewModel {

    private  ApiRepository<DataServerResponse<Survey>> mApiDataRepository;
    private  ApiRepository<ServerResponse> mApiRepository;

    public SurveyViewModel(){
        mApiDataRepository = new ApiRepository<>();
        mApiRepository = new ApiRepository<>();
    }

    public LiveData<ServerResponse> getResponse(){
        return mApiRepository.getResponse();
    }

    public LiveData<DataServerResponse<Survey>> getDataResponse(){
        return mApiDataRepository.getDataResponse();
    }

    public void fetchFeedBacks(String token, CampaignModel campaignModel){
        mApiDataRepository.processData(
                ServiceGenerator
                .getPromoterApi()
                .fetchSurveys(token, campaignModel.getPromoterId(),
                campaignModel.getCampaignId(), campaignModel.getCampaignLocationId()));
    }


}
