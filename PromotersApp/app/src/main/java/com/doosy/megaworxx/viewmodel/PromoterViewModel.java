package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.repository.PromoterRepository;

public class PromoterViewModel extends ViewModel {

    private PromoterRepository promoterRepository;


    public PromoterViewModel(){
        promoterRepository = PromoterRepository.getInstance();
    }

    public LiveData<DataServerResponse<Promoter>> getDataResponse(){
        return promoterRepository.getDataResponse();
    }

    public void login(LoginModel model) {
        promoterRepository.login( model);
    }

}
