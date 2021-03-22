package com.doosy.megaworxx.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.doosy.megaworxx.LoginActivity;
import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.viewmodel.ProfileViewModel;

public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private ProfileViewModel profileViewModel;
    private LiveData<DataServerResponse<Promoter>> mResponse;

    private Promoter mPromoter;

    private TextView tvFullName;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvId;
    private LinearLayout linearSignOut;

    public ProfileFragment (){
        setRetainInstance(true);
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

    }

    @Override
    public void retryLoad() {
        loadData();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        linearSignOut.setOnClickListener(this);
        if(mPromoter != null){
            displayUser();
        }else{
            loadData();
        }

    }

    @Override
    public void noContent(boolean hasContent){

    }

    private void loadData(){
        showLoading();
        profileViewModel.fetchProfileById(setting.getToken(), setting.getUserId());
        mResponse = profileViewModel.getDataResponse();

        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<Promoter>>() {
            @Override
            public void onChanged(DataServerResponse<Promoter> response) {
                hideLoading();
                if(response != null && response.isSuccessful()){
                    if(response.getDataList().size() > 0){
                        mPromoter = response.getDataList().get(0);
                        displayUser();
                        return;
                    }
                }

                showErrorPage();
            }
        });
    }

    private void displayUser(){
        tvFullName.setText(mPromoter.getFullName());
        tvName.setText(mPromoter.getFullName());
        tvEmail.setText(mPromoter.getEmail());
        tvPhone.setText(mPromoter.getPhone());
        tvId.setText(mPromoter.getIdNumber());
    }

    private void initViews(View view){
        tvFullName = view.findViewById(R.id.tvFullName);
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvId = view.findViewById(R.id.tvIdNumber);
        linearSignOut = view.findViewById(R.id.sign_out);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == linearSignOut.getId()){
            signOut();
        }
    }

    private void signOut(){
        setting.setIsLoggedIn(false);
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}