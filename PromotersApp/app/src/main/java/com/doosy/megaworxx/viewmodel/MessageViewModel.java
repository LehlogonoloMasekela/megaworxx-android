package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.Message;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.repository.ApiRepository;
import com.doosy.megaworxx.request.ServiceGenerator;

public class MessageViewModel extends ViewModel {
    private ApiRepository<DataServerResponse<Message>> mApiDataRepository;

    public MessageViewModel() {
        mApiDataRepository = new ApiRepository<>();
    }

    public LiveData<DataServerResponse<Message>> getDataResponse(){
        return mApiDataRepository.getDataResponse();
    }

    public void fetchMessages(String token, String id){
        mApiDataRepository.processData(ServiceGenerator.getPromoterApi().fetchMessages(token, id));
    }

    public void getMessageById(String token, String messageId){
        mApiDataRepository.processData(ServiceGenerator.getPromoterApi().getMessageById(token, messageId));
    }
}