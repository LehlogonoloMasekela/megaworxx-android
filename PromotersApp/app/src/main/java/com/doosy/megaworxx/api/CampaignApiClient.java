package com.doosy.megaworxx.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doosy.megaworxx.AppExecutors;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Form;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.entity.Today;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.model.TodayCampaignModel;
import com.doosy.megaworxx.request.ServiceGenerator;
import com.doosy.megaworxx.util.Constants;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class CampaignApiClient {

    private static CampaignApiClient instance;
    private MutableLiveData<ServerResponse> mResponse;
    private MutableLiveData<DataServerResponse<Promoter>> mLoginResponse;
    private MutableLiveData<DataServerResponse<Today>> mTodayResponse;
    private MutableLiveData<DataServerResponse<CampaignModel>> mTodayCampaignResponse;
    private MutableLiveData<DataServerResponse<Campaign>> campaignResponse;
    private MutableLiveData<DataServerResponse<Form>> mFormResponse;


    private CampaignRunnable mCampaignRunnable;
    private TodayDateRunnable mTodayRunnable;
    private TodayCampaignRunnable mTodayCampaignRunnable;
    private CheckRunnable mCheckInOutRunnable;
    private FetchFormRunnable mFetchFormRunnable;

    public static CampaignApiClient getInstance(){
        if(instance == null){
            instance = new CampaignApiClient();
        }
        return instance;
    }


    private CampaignApiClient(){
        mLoginResponse = new MutableLiveData<>();
        mTodayResponse = new MutableLiveData<>();
        mTodayCampaignResponse = new MutableLiveData<>();
        mResponse = new MutableLiveData<>();
        campaignResponse = new MutableLiveData<>();
        mFormResponse = new MutableLiveData<>();
    }

    public LiveData<DataServerResponse<Promoter>> getLoginResponse(){
        return mLoginResponse;
    }

    public LiveData<DataServerResponse<Today>> getTodayResponse(){
        return mTodayResponse;
    }

    public LiveData<DataServerResponse<CampaignModel>> getTodayCampaignResponse(){
        return mTodayCampaignResponse;
    }

    public LiveData<DataServerResponse<Form>> getFormResponse(){
        return mFormResponse;
    }

    public LiveData<DataServerResponse<Campaign>> getByIdResponse(){
        return campaignResponse;
    }


    public void getById(String token, String campaignId){
        if(mCampaignRunnable != null){
            mCampaignRunnable = null;
        }

        mCampaignRunnable = new CampaignRunnable(token, campaignId);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mCampaignRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    public LiveData<ServerResponse> getResponse(){
        return mResponse;
    }

    public void checkInOut(String token, CheckModel checkModel){
        if(mCheckInOutRunnable != null){
            mCheckInOutRunnable = null;
            mResponse = new MutableLiveData<>();
        }

        mCheckInOutRunnable = new CheckRunnable(token, checkModel);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mCheckInOutRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }
    public void fetchTodayDate(String token){
        if(mTodayRunnable != null){
            mTodayRunnable = null;
        }

        mTodayRunnable = new TodayDateRunnable(token);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mTodayRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    public void fetchFeedbackForm(String token){
        if(mFetchFormRunnable != null){
            mFetchFormRunnable = null;
        }

        mFetchFormRunnable = new FetchFormRunnable(token);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mFetchFormRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    public void fetchSurveyForm(String token){
        if(mFetchFormRunnable != null){
            mFetchFormRunnable = null;
        }

        mFetchFormRunnable = new FetchFormRunnable(token);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mFetchFormRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class FetchFormRunnable implements Runnable {
        String token;
        private boolean cancelRequest = false;

        public FetchFormRunnable(String token){
            this.cancelRequest = false;
            this.token = token;
        }

        @Override
        public void run() {

            try{

                Response<DataServerResponse<Form>> response = fetchFeedbackFormCall(token).execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){

                    DataServerResponse<Form> serverResponse = ((response.body()));

                    if(serverResponse != null && serverResponse.isSuccessful()){
                        List<Form> forms = serverResponse.getDataList();

                        mFormResponse.postValue(serverResponse);

                        Log.d(Constants.TAG,"200 status: " + response.body());
                        return;
                    }

                }

                Log.d(Constants.TAG,"Null on today date: " + response.body());
                mFormResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mFormResponse.postValue(null);
            }

        }
    }

    public void fetchTodayCampaign(String token, TodayCampaignModel campaignModel){
        if(mTodayCampaignRunnable != null){
            mTodayCampaignRunnable = null;
        }

        mTodayCampaignRunnable = new TodayCampaignRunnable(token, campaignModel);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mTodayCampaignRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class CampaignRunnable implements Runnable {

        String campaignId;
        String token;

        private boolean cancelRequest = false;

        public CampaignRunnable(String token,String campaignId){
            this.cancelRequest = false;
            this.campaignId = campaignId;
            this.token = token;
        }

        @Override
        public void run() {

            try{

                Response<DataServerResponse<Campaign>> response = campaignCall(token, campaignId).execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){

                    DataServerResponse<Campaign> serverResponse = response.body();

                    if(serverResponse != null && serverResponse.isSuccessful()){

                        campaignResponse.postValue(serverResponse);
                        return;
                    }

                }
                campaignResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                campaignResponse.postValue(null);
            }

        }
    }

    private class TodayCampaignRunnable implements Runnable {
        TodayCampaignModel campaignModel;
        String token;
        private boolean cancelRequest = false;

        public TodayCampaignRunnable(String token, TodayCampaignModel campaignModel){
            this.cancelRequest = false;
            this.campaignModel = campaignModel;
            this.token = token;
        }

        @Override
        public void run() {

            try{

                Response<DataServerResponse<CampaignModel>> response = todayCampaignCall(token, campaignModel).execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){

                    DataServerResponse<CampaignModel> serverResponse = ((response.body()));

                    if(serverResponse != null && serverResponse.isSuccessful()){
                        List<CampaignModel> todayList = serverResponse.getDataList();

                        mTodayCampaignResponse.postValue(serverResponse);

                        return;
                    }

                }

                mTodayCampaignResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mTodayCampaignResponse.postValue(null);
            }

        }
    }

    private class TodayDateRunnable implements Runnable {
        String token;
        private boolean cancelRequest = false;

        public TodayDateRunnable(String token){
            this.cancelRequest = false;
            this.token = token;
        }

        @Override
        public void run() {

            try{

                Response<DataServerResponse<String>> response = todayDateCall(token).execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){

                    DataServerResponse<String> serverResponse = ((response.body()));

                    if(serverResponse != null && serverResponse.isSuccessful()){
                        List<String> todayList = serverResponse.getDataList();

                        if(todayList != null && todayList.size() > 0){

                            Today today = new Today(todayList.get(0));
                            DataServerResponse<Today> dataServerResponse =
                                    new DataServerResponse<>(serverResponse.isSuccessful(), serverResponse.getMessages());
                            dataServerResponse.setData(today);
                            mTodayResponse.postValue(dataServerResponse);

                        }

                        return;
                    }

                }

                mTodayResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mTodayResponse.postValue(null);
            }

        }
    }

    private class CheckRunnable implements Runnable {

        CheckModel mCheckModel;
        String token;

        private boolean cancelRequest = false;

        public CheckRunnable(String token, CheckModel checkModel){
            this.cancelRequest = false;
            this.mCheckModel = checkModel;
            this.token = token;
        }

        @Override
        public void run() {

            try{

                Response<ServerResponse> response = checkInOutCall(token, mCheckModel).execute();

                if(cancelRequest){
                    return;
                }

                if(response.code() == 200){

                    ServerResponse serverResponse = ((response.body()));

                    if(serverResponse != null){
                        mResponse.postValue(serverResponse);

                        return;
                    }

                }

                mResponse.postValue(null);

            }catch (Exception e){

                mResponse.postValue(null);
            }

        }
    }


    private Call<DataServerResponse<Campaign>> campaignCall(String token, String campaignId){
        return ServiceGenerator.getPromoterApi().getCampaignById(token, campaignId);
    }

    private Call<ServerResponse> checkInOutCall(String token, CheckModel checkModel){
        return ServiceGenerator.getPromoterApi().checkInOut(token, checkModel);
    }

    private Call<DataServerResponse<Form>> fetchFeedbackFormCall(String token){
        return ServiceGenerator.getPromoterApi().fetchFeedbackForm(token);
    }

    private Call<DataServerResponse<Form>> fetchSurveyFormCall(String token){
        return ServiceGenerator.getPromoterApi().fetchSurveyForm(token);
    }

    private Call<DataServerResponse<String>> todayDateCall(String token){
        return ServiceGenerator.getPromoterApi().fetchTodayDate(token);
    }

    private Call<DataServerResponse<CampaignModel>> todayCampaignCall(String token, TodayCampaignModel campaignModel){
        return ServiceGenerator.getPromoterApi().fetchTodayCampaign(token, campaignModel.getPromoterId(), campaignModel.getCampaignDate());
    }


}
