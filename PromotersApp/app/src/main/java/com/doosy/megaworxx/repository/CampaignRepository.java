package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.CampaignApiClient;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Form;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.entity.Today;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.model.TodayCampaignModel;

public class CampaignRepository {
    private static CampaignRepository instance;
    private final CampaignApiClient mCampaignApiClient;

    public static CampaignRepository getInstance(){
        if(instance == null){
            instance = new CampaignRepository();
        }

        return  instance;
    }

    private CampaignRepository(){
        mCampaignApiClient = CampaignApiClient.getInstance();
    }

    public LiveData<DataServerResponse<Promoter>> getLoginResponse(){
        return mCampaignApiClient.getLoginResponse();
    }

    public void fetchTodayDate(String token){
        mCampaignApiClient.fetchTodayDate(token);
    }

    public void fetchFeedbackForm(String token){
        mCampaignApiClient.fetchFeedbackForm(token);
    }

    public void fetchSurveyForm(String token){
        mCampaignApiClient.fetchSurveyForm(token);
    }

    public void fetchTodayCampaign(String token, TodayCampaignModel campaignModel){
        mCampaignApiClient.fetchTodayCampaign(token, campaignModel);
    }

    public LiveData<DataServerResponse<Form>> getFormResponse(){
        return mCampaignApiClient.getFormResponse();
    }

    public LiveData<DataServerResponse<Today>> getTodayResponse(){
        return mCampaignApiClient.getTodayResponse();
    }

    public LiveData<DataServerResponse<Campaign>> getGetByIdResponse(){
        return mCampaignApiClient.getByIdResponse();
    }

    public void getCampaignById(String token, String campaignId){
        mCampaignApiClient.getById(token, campaignId);
    }

    public LiveData<DataServerResponse<CampaignModel>> getTodayCampaignResponse(){
        return mCampaignApiClient.getTodayCampaignResponse();
    }
    public LiveData<ServerResponse> getResponse(){
        return mCampaignApiClient.getResponse();
    }

    public void checkInOut(String token, CheckModel checkModel){
        mCampaignApiClient.checkInOut(token, checkModel);
    }

}
