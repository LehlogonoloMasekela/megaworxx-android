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
import com.doosy.megaworxx.adapter.SurveyAdapter;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Survey;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.StatusModel;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.Util;
import com.doosy.megaworxx.viewmodel.SurveyViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class SurveyFragment extends BaseFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private List<Survey> mCampaigns = new ArrayList<>();

    private RecyclerView mRecyclerviewSurvey;
    private SurveyAdapter mSurveyAdapter;
    private Campaign mCampaign;
    private CampaignModel campaignModel;


    private SurveyViewModel mSurveyViewModel;
    private LiveData<DataServerResponse<Survey>> mResponse;

    public SurveyFragment(){

    }

    public static SurveyFragment newInstance() {
        Log.d(Constants.TAG, "Creating Survey");
        SurveyFragment fragment = new SurveyFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSurveyViewModel = new ViewModelProvider(this).get(SurveyViewModel.class);
        mCampaign = ((CampaignActivity)getActivity()).getCampaign();
        campaignModel = ((CampaignActivity)getActivity()).getCampaignModel();
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

    public void loadData(){
        showLoading();
        mSurveyViewModel.fetchFeedBacks(setting.getToken(), campaignModel);
        mResponse = mSurveyViewModel.getDataResponse();
        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<Survey>>() {
            @Override
            public void onChanged(DataServerResponse<Survey> response) {
            hideLoading();
                if(response != null && response.isSuccessful()){

                    initRecyclerView(response.getDataList());
                    mSurveyAdapter.notifyDataSetChanged();
                    displayStatus();
                }

            }
        });
    }

    private void initViews(View view){
        mRecyclerviewSurvey = view.findViewById(R.id.recyclerViewCampaign);
    }

    private void initRecyclerView(List<Survey> surveys){

        mRecyclerviewSurvey.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mSurveyAdapter = new SurveyAdapter(surveys, getActivity());
        mRecyclerviewSurvey.setAdapter(mSurveyAdapter);
    }

    public void displayStatus(){
        StatusModel statusModel = ((CampaignActivity)getActivity()).getStatusModel();
        displayStatus(statusModel);
    }
}