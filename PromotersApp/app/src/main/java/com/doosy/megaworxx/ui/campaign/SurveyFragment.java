package com.doosy.megaworxx.ui.campaign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.SurveyAdapter;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.Survey;
import com.doosy.megaworxx.model.DataList;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.util.Util;

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
    private TextView campaignFragmentDate;
    public static SurveyFragment newInstance(int index) {
        SurveyFragment fragment = new SurveyFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCampaign = ((CampaignActivity)getActivity()).getCampaign();
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
        initRecyclerView(view);
    }

    private void initViews(View view){
        mRecyclerviewSurvey = view.findViewById(R.id.recyclerViewCampaign);
        campaignFragmentDate = view.findViewById(R.id.campaignFragmentDate);
    }

    private void initRecyclerView(View view){

        mRecyclerviewSurvey.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mSurveyAdapter = new SurveyAdapter(DataList.getSurveys(),getActivity());
        mRecyclerviewSurvey.setAdapter(mSurveyAdapter);
    }
}