package com.doosy.megaworxx.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.repository.CheckInOutRepository;

public class CheckInOutViewModel extends ViewModel {

    private CheckInOutRepository mCheckRepository;


    public CheckInOutViewModel(){
        mCheckRepository = CheckInOutRepository.getInstance();
    }

    public LiveData<ServerResponse> getCheckResponse(){
        return mCheckRepository.getCheckResponse();
    }

    public void check(String token,CheckModel checkModel){
        mCheckRepository.checkInOut(token, checkModel);
    }


}
