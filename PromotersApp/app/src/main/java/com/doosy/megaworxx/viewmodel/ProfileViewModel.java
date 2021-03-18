package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.CampaignForm;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.request.ServiceGenerator;

public class ProfileViewModel extends ViewModel {
    private ApiRepository<DataServerResponse<Promoter>> mApiDataRepository;

    public ProfileViewModel() {
        mApiDataRepository = new ApiRepository<>();
    }

    public LiveData<DataServerResponse<Promoter>> getDataResponse(){
        return mApiDataRepository.getDataResponse();
    }

    public void fetchProfileById(String token, String id){
        mApiDataRepository.processData(ServiceGenerator.getPromoterApi().getProfileById(token, id));
    }
}