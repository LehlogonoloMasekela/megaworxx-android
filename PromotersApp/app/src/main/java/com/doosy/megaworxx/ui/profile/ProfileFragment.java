package com.doosy.megaworxx.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.viewmodel.ProfileViewModel;

public class ProfileFragment extends BaseFragment {

    private ProfileViewModel profileViewModel;
    private LiveData<DataServerResponse<Promoter>> mResponse;

    private Promoter mPromoter;

    private TextView tvFullName;
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvId;

    public ProfileFragment (){

    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.fetchProfileById(setting.getToken(), setting.getUserId());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        if(mResponse != null){
            displayUser();
        }else{
            subscribe();
        }

    }

    private void subscribe(){
        showLoading();
        mResponse = profileViewModel.getDataResponse();

        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<Promoter>>() {
            @Override
            public void onChanged(DataServerResponse<Promoter> response) {
                hideLoading();
                if(response != null && response.isSuccessful()){
                    if(response.getDataList().size() > 0){
                        mPromoter = response.getDataList().get(0);
                        displayUser();
                    }
                }
            }
        });
    }

    private void displayUser(){
        tvFullName.setText(mPromoter.getFullName());
        tvFirstName.setText(mPromoter.getFirstName());
        tvLastName.setText(mPromoter.getSurname());
        tvEmail.setText(mPromoter.getEmail());
        tvPhone.setText(mPromoter.getPhone());
        tvId.setText(mPromoter.getIdNumber());
    }

    private void initViews(View view){
        tvFullName = view.findViewById(R.id.tvFullName);
        tvFirstName = view.findViewById(R.id.tvFirstName);
        tvLastName = view.findViewById(R.id.tvLastName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvId = view.findViewById(R.id.tvIdNumber);
    }
}