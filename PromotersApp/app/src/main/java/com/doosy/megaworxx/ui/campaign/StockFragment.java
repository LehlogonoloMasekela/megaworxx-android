package com.doosy.megaworxx.ui.campaign;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.StockAdapter;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.StatusModel;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.util.Util;
import com.doosy.megaworxx.viewmodel.StockViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class StockFragment extends BaseFragment {

    private Campaign mCampaign;
    private List<StockSaleBase> mStocks = new ArrayList<>();

    private RecyclerView mRecyclerviewStock;
    private StockAdapter mStockAdapter;

    private StockViewModel stockViewModel;
    private LiveData<DataServerResponse<StockSaleBase>> mResponse;
    private CampaignModel campaignModel;

    public StockFragment(){

    }

    public static StockFragment newInstance() {
        StockFragment fragment = new StockFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockViewModel = new ViewModelProvider(this).get(StockViewModel.class);

        campaignModel = ((CampaignActivity)getActivity()).getCampaignModel();
        mCampaign = ((CampaignActivity)getActivity()).getCampaign();
    }

    public void loadData(){
        showLoading();
        stockViewModel.fetchCampaignPromoterStock(setting.getToken(), campaignModel.getPromoterId(),
                campaignModel.getCampaignId(), campaignModel.getCampaignLocationId());
        mResponse = stockViewModel.getCampaignPromoterStocks();
        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<StockSaleBase>>() {
            @Override
            public void onChanged(DataServerResponse<StockSaleBase> response) {
                hideLoading();
                if(response != null && response.isSuccessful()){

                    initRecyclerView(response.getDataList());
                    mStockAdapter.notifyDataSetChanged();
                    displayStatus();
                }

            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_campaign_tab;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        loadData();

        if(mCampaign != null){
            String date = getActivity().getString(R.string.campaign_date,
                    Util.formatDate(mCampaign.getDateCreated()) );
            displayCampaignDate(date);
        }

    }

    public void displayStatus(){
        StatusModel statusModel = ((CampaignActivity)getActivity()).getStatusModel();
        displayStatus(statusModel);
    }

    private void initViews(View view){
        mRecyclerviewStock = view.findViewById(R.id.recyclerViewCampaign);
    }

    private void initRecyclerView(List<StockSaleBase> stocks){

        mRecyclerviewStock.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mStockAdapter = new StockAdapter(stocks, getActivity());
        mRecyclerviewStock.setAdapter(mStockAdapter);
    }
}