package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.entity.Form;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.entity.Today;
import com.doosy.megaworxx.model.CampaignFilterModel;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.model.TodayCampaignModel;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.repository.CampaignRepository;
import com.doosy.megaworxx.request.ServiceGenerator;

public class CampaignViewModel extends ViewModel {

    private CampaignRepository mCampaignRepository;

    //A new generic method to replace the old way of pulling data
    private ApiRepository<DataServerResponse<CampaignModel>> mApiDataRepository;

    public CampaignViewModel(){
        mCampaignRepository = CampaignRepository.getInstance();
        mApiDataRepository = new ApiRepository<>();
    }

    public LiveData<DataServerResponse<Today>> getTodayResponse(){
        return mCampaignRepository.getTodayResponse();
    }
    public LiveData<DataServerResponse<CampaignModel>> getTodayCampaignResponse(){
        return mCampaignRepository.getTodayCampaignResponse();
    }

    public LiveData<DataServerResponse<CampaignModel>> getDataResponse(){
        return mApiDataRepository.getDataResponse();
    }

    public void fetchFilteredCampaign(String token, CampaignFilterModel model){
        mApiDataRepository.processData(
                ServiceGenerator
                        .getPromoterApi()
                        .fetchFilteredCampaign(token, model.getPromoterId(),
                                model.getFromDate(),model.getToDate()));
    }


    public void getCampaignById(String token, String campaignId){
        mCampaignRepository.getCampaignById(token, campaignId);
    }

    public void fetchTodayDate(String token){
        mCampaignRepository.fetchTodayDate(token);
    }

    public void fetchTodayCampaign(String token, TodayCampaignModel campaignModel){
        mCampaignRepository.fetchTodayCampaign(token, campaignModel);
    }

    public LiveData<DataServerResponse<Campaign>> getCampaignResponse(){
        return mCampaignRepository.getGetByIdResponse();
    }

    public LiveData<ServerResponse> getResponse(){
        return mCampaignRepository.getResponse();
    }

    public LiveData<DataServerResponse<Form>> getFormResponse(){
        return mCampaignRepository.getFormResponse();
    }

    public void check(String token,CheckModel checkModel){
        mCampaignRepository.checkInOut(token, checkModel);
    }

    public void fetchFeedbackForm(String token){
        mCampaignRepository.fetchFeedbackForm(token);
    }

    public void fetchSurveyForm(String token){
        mCampaignRepository.fetchSurveyForm(token);
    }

}
