package com.doosy.megaworxx.ui.campaign;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
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

    private List<Survey> mCampaigns = new ArrayList<>();
    private FrameLayout noContent;
    private RecyclerView mRecyclerviewSurvey;
    private SurveyAdapter mSurveyAdapter;
    private Campaign mCampaign;
    private CampaignModel campaignModel;


    private SurveyViewModel mSurveyViewModel;
    private LiveData<DataServerResponse<Survey>> mResponse;

    public SurveyFragment(){
        setRetainInstance(true);
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
    public void retryLoad() {
        loadData();
    }

    @Override
    public void noContent(boolean hasContent){
        mRecyclerviewSurvey.setVisibility(hasContent ? View.VISIBLE : View.GONE);
        noContent.setVisibility(hasContent ? View.GONE : View.VISIBLE);
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
            String date = getActivity().getString(R.string.campaign_date, Util.formatDate(mCampaign.getDateCreated()) );
            displayCampaignDate(date);
        }
        displayStatus(null);

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
                    if(response.getDataList().size() > 0) {
                        initRecyclerView(response.getDataList());
                        mSurveyAdapter.notifyDataSetChanged();
                        displayStatus();
                        noContent(true);
                    }else{
                        noContent(false);
                    }
                    return;
                }
                showErrorPage();

            }
        });
    }

    private void initViews(View view){
        mRecyclerviewSurvey = view.findViewById(R.id.recyclerViewCampaign);
        noContent = view.findViewById(R.id.noContent);
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