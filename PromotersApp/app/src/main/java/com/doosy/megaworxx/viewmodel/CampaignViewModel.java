package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.entity.Today;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.model.TodayCampaignModel;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.repository.CampaignRepository;

public class CampaignViewModel extends ViewModel {

    private CampaignRepository mCampaignRepository;
    private ApiRepository<Campaign> mApiRepository;
    private MutableLiveData<Promoter> mPerson = new MutableLiveData<>();

    public CampaignViewModel(){
        mCampaignRepository = CampaignRepository.getInstance();
    }


    public LiveData<DataServerResponse<Today>> getTodayResponse(){
        return mCampaignRepository.getTodayResponse();
    }
    public LiveData<DataServerResponse<CampaignModel>> getTodayCampaignResponse(){
        return mCampaignRepository.getTodayCampaignResponse();
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

    public void check(String token,CheckModel checkModel){
        mCampaignRepository.checkInOut(token, checkModel);
    }

}
