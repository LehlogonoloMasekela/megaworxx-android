package com.doosy.megaworxx.ui.campaign;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.SalesAdapter;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.StatusModel;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.Util;
import com.doosy.megaworxx.viewmodel.SaleViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SalesFragment extends BaseFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private List<StockSaleBase> mSales = new ArrayList<>();

    private RecyclerView mRecyclerviewSales;
    private SalesAdapter mSalesAdapter;
    private Campaign mCampaign;
    private SaleViewModel saleViewModel;
    private LiveData<DataServerResponse<StockSaleBase>> mResponse;
    private CampaignModel campaignModel;

    public SalesFragment() {

    }

    public static SalesFragment newInstance() {
        Log.d(Constants.TAG, "Creating Sale");
        SalesFragment fragment = new SalesFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        campaignModel = ((CampaignActivity)getActivity()).getCampaignModel();
        mCampaign = ((CampaignActivity)getActivity()).getCampaign();
    }

    public void loadData(){
        showLoading();
        saleViewModel.fetchPromoterSale(setting.getToken(), campaignModel.getPromoterId(),
                campaignModel.getCampaignId(), campaignModel.getCampaignLocationId());
        mResponse = saleViewModel.getDataResponse();
        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<StockSaleBase>>() {
            @Override
            public void onChanged(DataServerResponse<StockSaleBase> response) {
                hideLoading();
                if(response != null && response.isSuccessful()){
                    initRecyclerView(response.getDataList());
                    mSalesAdapter.notifyDataSetChanged();
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
        if(mCampaign != null){
            String date = getActivity().getString(R.string.campaign_date, Util.formatDate(mCampaign.getDateCreated()) );
            displayCampaignDate(date);
        }
        loadData();
    }

    private void initViews(View view){
        mRecyclerviewSales = view.findViewById(R.id.recyclerViewCampaign);
    }

    private void initRecyclerView(List<StockSaleBase> sales){

        mRecyclerviewSales.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mSalesAdapter = new SalesAdapter(sales, getActivity());
        mRecyclerviewSales.setAdapter(mSalesAdapter);
    }

    public void displayStatus(){

        StatusModel statusModel = ((CampaignActivity)getActivity()).getStatusModel();
        displayStatus(statusModel);

    }
}