package com.doosy.megaworxx.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doosy.megaworxx.AppExecutors;
import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.PromoterSaleModel;
import com.doosy.megaworxx.model.SaleModel;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.request.ServiceGenerator;
import com.doosy.megaworxx.util.Constants;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class SaleApiClient {
    private static SaleApiClient instance;

    private MutableLiveData<DataServerResponse<StockSaleBase>> mSaleResponse;
    private MutableLiveData<ServerResponse> mResponse;
    private SalesRunnable mSalesRunnable;
    private SaveSaleRunnable mSaveSaleRunnable;
    private PromoterSaleRunnable mPromoterSaleRunnable;

    public static SaleApiClient getInstance(){
        if(instance == null){
            instance = new SaleApiClient();
        }
        return instance;
    }

    private SaleApiClient(){
        mResponse = new MutableLiveData<>();
        mSaleResponse = new MutableLiveData<>();
    }

    public LiveData<DataServerResponse<StockSaleBase>> getSaleResponse(){
        return mSaleResponse;
    }

    public LiveData<ServerResponse> getResponse(){
        return mResponse;
    }

    public void fetchSale(String token,PromoterSaleModel promoterSaleModel){
        if(mSalesRunnable != null){
            mSalesRunnable = null;
        }

        mSalesRunnable = new SalesRunnable(token, promoterSaleModel);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mSalesRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    public void saveSale(String token, SaleModel saleModel){
        mResponse = new MutableLiveData<>();
        if(mSaveSaleRunnable != null){
            mSaveSaleRunnable = null;
        }

        mSaveSaleRunnable = new SaveSaleRunnable(token, saleModel);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mSaveSaleRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class SaveSaleRunnable implements Runnable {
        SaleModel mSaleModel;
        String mToken;

        private boolean cancelRequest = false;

        public SaveSaleRunnable(String token, SaleModel saleModel){
            this.cancelRequest = false;
            this.mSaleModel = saleModel;
            this.mToken = token;
        }

        @Override
        public void run() {

            try{

                Response response = saveSaleCall(mToken, mSaleModel).execute();

                if(cancelRequest){
                    return;
                }
                Log.d(Constants.TAG,"Before if : "+response.body());
                if(response.code() == 200){

                    ServerResponse serverResponse = ((ServerResponse)(response.body()));

                    if(serverResponse != null){
                        mResponse.postValue(serverResponse);
                        return;
                    }
                    Log.d(Constants.TAG,"Model: "+response.body());
                }

                mResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mResponse.postValue(null);
            }

        }
    }
    private class SalesRunnable implements Runnable {
        PromoterSaleModel mPromoterSaleModel;
        String mToken;

        private boolean cancelRequest = false;

        public SalesRunnable(String token,PromoterSaleModel promoterSaleModel){
            this.cancelRequest = false;
            this.mPromoterSaleModel = promoterSaleModel;
            this.mToken = token;
        }

        @Override
        public void run() {

            try{

                Response response = fetchSales(mToken, mPromoterSaleModel).execute();

                if(cancelRequest){
                    return;
                }
                Log.d(Constants.TAG,"Before if : "+response.body());
                if(response.code() == 200){

                    DataServerResponse<StockSaleBase> serverResponse = ((DataServerResponse<StockSaleBase>)(response.body()));

                    if(serverResponse != null){
                        mSaleResponse.postValue(serverResponse);
                        Log.d(Constants.TAG,"Array size: "+serverResponse.getDataList().size());
                        return;
                    }
                    Log.d(Constants.TAG,"Model: "+response.body());
                }

                mSaleResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mSaleResponse.postValue(null);
            }

        }
    }

    public void fetchPromoterSale(String token,String promoterId,String campaignId, String campaignLocationId){
        mSaleResponse = new MutableLiveData<>();

        if(mPromoterSaleRunnable != null){
            mPromoterSaleRunnable = null;
        }

        mPromoterSaleRunnable = new PromoterSaleRunnable(token, promoterId, campaignId, campaignLocationId);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mPromoterSaleRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }
    private class PromoterSaleRunnable implements Runnable {
        String mPromoterId;
        String mCampaignId;
        String mCampaignLocationId;
        String mToken;

        private boolean cancelRequest = false;

        public PromoterSaleRunnable(String token,String promoterId,String campaignId, String campaignLocationId){
            this.cancelRequest = false;
            mPromoterId = promoterId;
            mCampaignId = campaignId;
            mCampaignLocationId = campaignLocationId;
            mToken = token;
        }

        @Override
        public void run() {

            try{

                Response response = getPromoterSales(mToken, mPromoterId, mCampaignId, mCampaignLocationId).execute();

                if(cancelRequest){
                    return;
                }
                Log.d(Constants.TAG,"Promoter Stock Before if : "+response.body());
                Log.d(Constants.TAG,response.body().toString());
                if(response.code() == 200){

                    DataServerResponse<StockSaleBase> serverResponse = ((DataServerResponse<StockSaleBase>)(response.body()));

                    if(serverResponse != null){
                        mSaleResponse.postValue(serverResponse);
                        return;
                    }
                    Log.d(Constants.TAG,"Promoter Stock Arrays Size: "+serverResponse.getDataList().size());
                }

                mSaleResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                mSaleResponse.postValue(null);
            }

        }
    }

    private Call<ServerResponse> saveSaleCall(String token, SaleModel model){

        return ServiceGenerator.getPromoterApi().saveCampaignSale(token, model);
    }

    private Call<DataServerResponse<StockSaleBase>> fetchSales(String token,PromoterSaleModel model){

        return ServiceGenerator.getPromoterApi().fetchPromoterSales(token, model.getPromoterId(),
                model.getCampaignId(), model.getCampaignLocationId());
    }

    private Call<DataServerResponse<StockSaleBase>> getPromoterSales(String token,String promoterId,String campaignId, String campaignLocationId){
        return ServiceGenerator.getPromoterApi().getPromoterSales(token, promoterId,campaignId,campaignLocationId);
    }

}
