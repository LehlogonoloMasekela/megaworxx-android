package com.doosy.megaworxx.ui.campaign;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.doosy.megaworxx.entity.Stock;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.Util;
import com.doosy.megaworxx.viewmodel.StockViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class StockFragment extends BaseFragment {

    private static final String ARG_CAMPAIGN_ID = "campaign_id";
    private Campaign mCampaign;
    private List<Stock> mStocks = new ArrayList<>();

    private RecyclerView mRecyclerviewStock;
    private TextView campaignFragmentDate;
    private StockAdapter mStockAdapter;

    private StockViewModel stockViewModel;
    private LiveData<DataServerResponse<Stock>> mResponse;
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
        if (campaignModel != null) {
            Log.d(Constants.TAG, campaignModel.toString());
            stockViewModel.fetchCampaignPromoterStock(setting.getToken(), campaignModel.getPromoterId(),
                    campaignModel.getCampaignId(), campaignModel.getCampaignLocationId());
        }

        mCampaign = ((CampaignActivity)getActivity()).getCampaign();

    }

    public void loadData(){
        stockViewModel.fetchCampaignPromoterStock(setting.getToken(), campaignModel.getPromoterId(),
                campaignModel.getCampaignId(), campaignModel.getCampaignLocationId());
        mResponse = stockViewModel.getCampaignPromoterStocks();
        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<Stock>>() {
            @Override
            public void onChanged(DataServerResponse<Stock> response) {

                if(response != null && response.isSuccessful()){

                    initRecyclerView(response.getDataList());
                    mStockAdapter.notifyDataSetChanged();
                    Log.d(Constants.TAG, "Loading stock");
                }

            }
        });
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_campaign_tab, container, false);

        return root;
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
            campaignFragmentDate.setText(Util.formatDate(mCampaign.getDateCreated()));
        }
    }

    private void initViews(View view){
        mRecyclerviewStock = view.findViewById(R.id.recyclerViewCampaign);
        campaignFragmentDate = view.findViewById(R.id.campaignFragmentDate);
    }

    private void initRecyclerView(List<Stock> stocks){

        mRecyclerviewStock.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mStockAdapter = new StockAdapter(stocks, getActivity());
        mRecyclerviewStock.setAdapter(mStockAdapter);
    }
}