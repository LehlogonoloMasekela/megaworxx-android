package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.entity.QuestionnaireResponse;
import com.doosy.megaworxx.model.CampaignForm;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.request.ServiceGenerator;

public class QuestionnaireDetailViewModel extends ViewModel {

    private  ApiRepository<DataServerResponse<QuestionnaireResponse>> mApiDataRepository;

    public QuestionnaireDetailViewModel(){
        mApiDataRepository = new ApiRepository<>();
    }

    public LiveData<DataServerResponse<QuestionnaireResponse>> getDataResponse(){
        return mApiDataRepository.getDataResponse();
    }

    public void fetchQuestionnaireDetail(String token, String formId){
        mApiDataRepository.processData(
                ServiceGenerator
                .getPromoterApi()
                .fetchQuestionnaireDetail(token, formId));
    }


}
