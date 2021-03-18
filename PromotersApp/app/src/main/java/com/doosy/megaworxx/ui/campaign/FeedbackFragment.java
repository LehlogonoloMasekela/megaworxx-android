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
import com.doosy.megaworxx.adapter.FeedbackAdapter;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.StatusModel;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.Util;
import com.doosy.megaworxx.viewmodel.FeedBackViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class FeedbackFragment extends BaseFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private List<Feedback> mFeedBacks = new ArrayList<>();

    private RecyclerView mRecyclerviewFeedback;
    private FeedbackAdapter mFeedbackAdapter;
    private Campaign mCampaign;
    private CampaignModel campaignModel;

    private FeedBackViewModel mFeedBackViewModel;
    private LiveData<DataServerResponse<Feedback>> mResponse;

    public FeedbackFragment(){

    }

    public static FeedbackFragment newInstance() {
        Log.d(Constants.TAG, "Creating Feedback");
        FeedbackFragment fragment = new FeedbackFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedBackViewModel = new ViewModelProvider(this).get(FeedBackViewModel.class);

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
        mFeedBackViewModel.fetchFeedBacks(setting.getToken(), campaignModel);
        mResponse = mFeedBackViewModel.getDataResponse();
        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<Feedback>>() {
            @Override
            public void onChanged(DataServerResponse<Feedback> response) {
                hideLoading();

                if(response != null && response.isSuccessful()){

                    initRecyclerView(response.getDataList());
                    mFeedbackAdapter.notifyDataSetChanged();
                    displayStatus();
                }

            }
        });
    }

    public void displayStatus(){

        StatusModel statusModel = ((CampaignActivity)getActivity()).getStatusModel();
        displayStatus(statusModel);

    }

    private void initViews(View view){
        mRecyclerviewFeedback = view.findViewById(R.id.recyclerViewCampaign);

    }

    private void initRecyclerView(List<Feedback> feedbacks){

        mRecyclerviewFeedback.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mFeedbackAdapter = new FeedbackAdapter(feedbacks, getActivity());
        mRecyclerviewFeedback.setAdapter(mFeedbackAdapter);
    }
}