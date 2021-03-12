package com.doosy.megaworxx.repository;

import androidx.lifecycle.LiveData;

import com.doosy.megaworxx.api.CheckApiClient;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.ServerResponse;

public class CheckInOutRepository {
    private final CheckApiClient mCheckInOutApiClient;

    public static CheckInOutRepository getInstance(){
        return new CheckInOutRepository();
    }

    private CheckInOutRepository(){
        mCheckInOutApiClient = CheckApiClient.getInstance();
    }

    public LiveData<ServerResponse> getCheckResponse(){
        return mCheckInOutApiClient.getCheckResponse();
    }

    public void checkInOut(String token, CheckModel checkModel){
        mCheckInOutApiClient.checkInOut(token, checkModel);
    }


}
