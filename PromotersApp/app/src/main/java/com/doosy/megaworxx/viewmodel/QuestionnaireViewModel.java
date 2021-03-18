package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.entity.Form;
import com.doosy.megaworxx.model.CampaignForm;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.request.ServiceGenerator;

public class QuestionnaireViewModel extends ViewModel {

    private  ApiRepository<DataServerResponse<Feedback>> mApiDataRepository;
    private  ApiRepository<DataServerResponse<Form>> mFormDataResponse;
    private  ApiRepository<ServerResponse> mApiRepository;

    public QuestionnaireViewModel(){
        mApiDataRepository = new ApiRepository<>();
        mApiRepository = new ApiRepository<>();
        mFormDataResponse = new ApiRepository<>();
    }

    public LiveData<ServerResponse> getResponse(){
        return mApiRepository.getResponse();
    }

    public LiveData<DataServerResponse<Feedback>> getDataResponse(){
        return mApiDataRepository.getDataResponse();
    }

    public void saveQuestionnaireForm(String token, CampaignForm campaignForm){
        mApiRepository.process(ServiceGenerator.getPromoterApi().saveCampaignForm(token, campaignForm));
    }

    public LiveData<DataServerResponse<Form>> getQuestionsResponse(){
        return mFormDataResponse.getDataResponse();
    }

    public void fetchFeedBackForm(String token){
        mFormDataResponse.processData(ServiceGenerator
                .getPromoterApi().fetchFeedbackForm(token));
    }

    public void fetchSurveyForm(String token){
        mFormDataResponse.processData(ServiceGenerator
                .getPromoterApi().fetchSurveyForm(token));
    }



}
