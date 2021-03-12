package com.doosy.megaworxx.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.doosy.megaworxx.AppExecutors;
import com.doosy.megaworxx.entity.Stock;
import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.model.AddStockModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.request.ServiceGenerator;
import com.doosy.megaworxx.util.Constants;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class StockItemApiClient {
    private static StockItemApiClient instance;

    private MutableLiveData<DataServerResponse<StockItem>> dataResponse;
    private MutableLiveData<ServerResponse> mResponse;
    private StockItemRunnable mStockItemRunnable;
    private AddStockRunnable mAddStockRunnable;

    public static StockItemApiClient getInstance(){
        if(instance == null){
            instance = new StockItemApiClient();
        }
        return instance;
    }

    private StockItemApiClient(){
        dataResponse = new MutableLiveData<>();
        mResponse = new MutableLiveData<>();
    }

    public LiveData<DataServerResponse<StockItem>> getDataResponse(){
        return dataResponse;
    }

    public LiveData<ServerResponse> getResponse(){
        return mResponse;
    }

    public void fetchStockItems(String token){

        if(mStockItemRunnable != null){
            mStockItemRunnable = null;
        }

        mStockItemRunnable = new StockItemRunnable( token);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mStockItemRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    public void addStock(String token, List<AddStockModel> addStockModel) {
        mResponse = new MutableLiveData<>();
        if(mAddStockRunnable != null){
            mAddStockRunnable = null;
        }

        mAddStockRunnable = new AddStockRunnable(token, addStockModel);

        final Future handler = AppExecutors.getInstance().
                getNetworkIO().submit(mAddStockRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class StockItemRunnable implements Runnable {

        private boolean cancelRequest = false;
        private String mToken;
        public StockItemRunnable(String token){
            this.cancelRequest = false;
            mToken = token;
        }

        @Override
        public void run() {

            try{

                Response response = fetchItemCall(mToken).execute();

                if(cancelRequest){
                    return;
                }
                Log.d(Constants.TAG,"Before if : "+response.body());
                if(response.code() == 200){

                    DataServerResponse<StockItem> serverResponse = ((DataServerResponse<StockItem>)(response.body()));

                    if(serverResponse != null){
                        dataResponse.postValue(serverResponse);
                        Log.d(Constants.TAG,"Array size: "+serverResponse.getDataList().size());
                        return;
                    }
                    Log.d(Constants.TAG,"Model: "+response.body());
                }

                dataResponse.postValue(null);

            }catch (Exception e){
                Log.d(Constants.TAG,"Exception: " + e.fillInStackTrace());
                dataResponse.postValue(null);
            }

        }
    }

    private class AddStockRunnable implements Runnable {
        List<AddStockModel> mAddStockModel;
        String mToken;

        private boolean cancelRequest = false;

        public AddStockRunnable(String token,List<AddStockModel> addStockModel){
            this.cancelRequest = false;
            this.mAddStockModel = addStockModel;
            this.mToken = token;
        }

        @Override
        public void run() {

            try{

                Response response = addStockCall(mToken, mAddStockModel).execute();

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


    private Call<ServerResponse> addStockCall(String token,List<AddStockModel> addStockModel){
        return ServiceGenerator.getPromoterApi().saveCampaignStock(token, addStockModel);
    }

    private Call<DataServerResponse<StockItem>> fetchItemCall(String token){
        return ServiceGenerator.getPromoterApi().fetchItems(token);
    }
    private Call<DataServerResponse<Stock>> getPromoterStocks(String token,String promoterId,String campaignId, String campaignLocationId){
        return ServiceGenerator.getPromoterApi().getPromoterStocks(token, promoterId,campaignId,campaignLocationId);
    }

}
