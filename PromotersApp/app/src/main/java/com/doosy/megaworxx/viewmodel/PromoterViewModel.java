package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.repository.PromoterRepository;

public class PromoterViewModel extends ViewModel {

    private PromoterRepository mPromoterRepository;

    private MutableLiveData<Promoter> mPerson = new MutableLiveData<>();

    public PromoterViewModel(){
        mPromoterRepository = PromoterRepository.getInstance();
    }

    public LiveData<DataServerResponse<Promoter>> getLoginResponse(){
        return mPromoterRepository.getLoginResponse();
    }

    public void login(LoginModel loginModel){
        mPromoterRepository.login(loginModel);
    }

}
