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
import com.doosy.megaworxx.adapter.SalesAdapter;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Sales;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.PromoterSaleModel;
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

    private List<Sales> mSales = new ArrayList<>();

    private RecyclerView mRecyclerviewSales;
    private SalesAdapter mSalesAdapter;
    private Campaign mCampaign;
    private TextView campaignFragmentDate;
    private SaleViewModel saleViewModel;
    private LiveData<DataServerResponse<Sales>> mResponse;
    private CampaignModel campaignModel;

    public static SalesFragment newInstance(int index) {
        SalesFragment fragment = new SalesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        campaignModel = ((CampaignActivity)getActivity()).getCampaignModel();
        if (campaignModel != null) {
            Log.d(Constants.TAG, campaignModel.toString());
            saleViewModel.fetchPromoterSale(setting.getToken(), campaignModel.getPromoterId(),
                    campaignModel.getCampaignId(), campaignModel.getCampaignLocationId());
        }
        mCampaign = ((CampaignActivity)getActivity()).getCampaign();
    }

    public void reloadData(){
        saleViewModel.fetchPromoterSale(setting.getToken(), campaignModel.getPromoterId(),
                campaignModel.getCampaignId(), campaignModel.getCampaignLocationId());
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
        if(mCampaign != null){
            campaignFragmentDate.setText(Util.formatDate(mCampaign.getDateCreated()));
        }
        mResponse = saleViewModel.getDataResponse();
        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<Sales>>() {
            @Override
            public void onChanged(DataServerResponse<Sales> response) {

                if(response != null && response.isSuccessful()){
                    initRecyclerView(response.getDataList());
                }

            }
        });
    }

    private void initViews(View view){
        mRecyclerviewSales = view.findViewById(R.id.recyclerViewCampaign);
        campaignFragmentDate = view.findViewById(R.id.campaignFragmentDate);
    }

    private void initRecyclerView(List<Sales> sales){

        mRecyclerviewSales.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mSalesAdapter = new SalesAdapter(sales, getActivity());
        mRecyclerviewSales.setAdapter(mSalesAdapter);
    }
}