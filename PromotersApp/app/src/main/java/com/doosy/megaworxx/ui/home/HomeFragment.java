package com.doosy.megaworxx.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.CampaignAdapter;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.model.ClientToken;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.TodayCampaignModel;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.Util;
import com.doosy.megaworxx.viewmodel.CampaignViewModel;
import com.doosy.megaworxx.viewmodel.TokenViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private CampaignViewModel mCampaignViewModel;
    private LiveData<DataServerResponse<CampaignModel>> mResponse;
    private List<CampaignModel> mCampaignModels = new ArrayList<>();
    private LiveData<DataServerResponse<ClientToken>> mTokenResponse;

    private RecyclerView mRecyclerviewCampaign;
    private CampaignAdapter mCampaignAdapter;

    private TokenViewModel mTokenViewModel;
    private FrameLayout noContent;

    public HomeFragment (){
        setRetainInstance(true);
    }

    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }


    @Override
    public void noContent(boolean hasContent){
        mRecyclerviewCampaign.setVisibility(hasContent ? View.VISIBLE : View.GONE);
        noContent.setVisibility(hasContent ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCampaignViewModel = ViewModelProviders.of(this).get(CampaignViewModel.class);
        mTokenViewModel = ViewModelProviders.of(this).get(TokenViewModel.class);
    }

    @Override
    public void retryLoad() {
        if(mTokenTimer.isTokenAlive()){
            loadData();
        }else{
            getToken();
        }
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        showLoading();

        if(mTokenTimer.isTokenAlive()){
            loadData();
        }else{
            getToken();
        }

    }

    private TodayCampaignModel getModel(){
        TodayCampaignModel model = new
                TodayCampaignModel(setting.getUserId(), Util.convertToFormat(setting.getTodayDate()));
        return model;
    }

    private void getToken(){

        mTokenViewModel.processApi();
        mTokenResponse = mTokenViewModel.getResponse();

        mTokenResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<ClientToken>>() {
            @Override
            public void onChanged(DataServerResponse<ClientToken> response) {
                if(response != null ){
                    if(response.getData() != null)
                    {
                        //Store token and update time to expire
                        String token = response.getData().getAccessToken();
                        setting.setToken(token);
                        mTokenTimer.setTime(2);
                        loadData();
                    }else{
                        Toast.makeText(getContext(), "Failed to authorize.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showErrorPage();
                }
            }
        });
    }

    private void loadData() {
        mCampaignViewModel.fetchTodayCampaign(setting.getToken(), getModel());
        mResponse = mCampaignViewModel.getTodayCampaignResponse();

        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<CampaignModel>>() {
            @Override
            public void onChanged(DataServerResponse<CampaignModel> response) {
                hideLoading();
                mResponse.removeObserver(this);
                if(mResponse.hasActiveObservers()){
                    mResponse.removeObservers(getActivity());
                }

                if(response.isSuccessful()){
                    mCampaignModels = response.getDataList();
                    if(mCampaignModels != null && mCampaignModels.size() > 0) {
                        initRecyclerView();
                        noContent(true);
                    }
                    else
                        noContent(false);

                    return;
                }

                showErrorPage();

            }
        });
    }



    private void initViews(View view){
        mRecyclerviewCampaign = view.findViewById(R.id.recyclerViewCampaign);
        noContent = view.findViewById(R.id.noContent);
    }

    private void initRecyclerView(){
        mRecyclerviewCampaign.setVisibility(View.VISIBLE);
        mRecyclerviewCampaign.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mCampaignAdapter = new CampaignAdapter(mCampaignModels, getActivity());
        mRecyclerviewCampaign.setAdapter(mCampaignAdapter);
    }
}