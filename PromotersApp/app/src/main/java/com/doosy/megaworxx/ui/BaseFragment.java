package com.doosy.megaworxx.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.model.StatusModel;
import com.doosy.megaworxx.util.Setting;
import com.doosy.megaworxx.util.TokenTimer;
import com.doosy.megaworxx.util.Util;

public abstract class BaseFragment extends Fragment {
    private LinearLayout noContent;
    private FrameLayout fragmentContent;
    private ProgressBar mProgressBar;

    private LinearLayout successLayout;
    private TextView tvSuccessMessage;
    private LinearLayout errorLayout;
    private TextView tvErrorMessage;
    private View rootView;
    public Setting setting;
    public TextView noContentCaption;
    private TextView txtReload;
    public TokenTimer mTokenTimer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting = new Setting(getContext());
        mTokenTimer = new TokenTimer(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup parent, Bundle savedInstanceState)
    {
        int resourceId = getLayoutRes();

        if(resourceId == 0) return super.onCreateView(inflater, parent, savedInstanceState);

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, parent, false);
            initView(rootView);
            LayoutInflater.from(requireContext()).inflate(resourceId, fragmentContent, true);
        }

        return rootView;
    }

    protected void showErrorPage(){
        noContentCaption.setText(getActivity().getString(R.string.general_error_message));
        resetAll();
        noContent.setVisibility(View.VISIBLE);
        txtReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                retryLoad();
            }
        });
    }

    public abstract void retryLoad();

    private void initView(View rootView){
        mProgressBar = rootView.findViewById(R.id.progress_bar);
        fragmentContent = rootView.findViewById(R.id.fragment_content);

        successLayout = rootView.findViewById(R.id.successLayout);
        tvSuccessMessage = rootView.findViewById(R.id.tvSuccessMessage);
        errorLayout = rootView.findViewById(R.id.errorLayout);
        tvErrorMessage = rootView.findViewById(R.id.tvErrorMessage);
        noContent = rootView.findViewById(R.id.noContent);
        noContentCaption = rootView.findViewById(R.id.tvConnectionMessage);
        txtReload = rootView.findViewById(R.id.txtRetry);
    }

    public void showError(String errorMsg){
        tvErrorMessage.setText(errorMsg);
        errorLayout.setVisibility(View.VISIBLE);
    }

    public void showSuccess(String successMessage){
        tvSuccessMessage.setText(successMessage);
        successLayout.setVisibility(View.VISIBLE);
    }

    public void showLoading(){
        resetAll();
        fragmentContent.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading(){
        resetAll();
        mProgressBar.setVisibility(View.GONE);
        fragmentContent.setVisibility(View.VISIBLE);
    }

    private void resetAll(){
        errorLayout.setVisibility(View.GONE);
        noContent.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        fragmentContent.setVisibility(View.GONE);
    }

    public abstract int getLayoutRes();

    public void displayCampaignDate(String campaignDate){
        ((TextView) rootView.findViewById(R.id.campaignFragmentDate)).setText(campaignDate);
    }

    public void displayStatus(StatusModel statusModel){

        ImageView imgInIndicator = rootView.findViewById(R.id.imgCheckInStatus);
        ImageView imgOutIndicator = rootView.findViewById(R.id.imgCheckOutStatus);
        TextView tvInTime = rootView.findViewById(R.id.tvCheckInTime);
        TextView tvOutTime = rootView.findViewById(R.id.tvCheckOutTime);

        String inTime = getActivity().getString(R.string.campaign_check_in_time,"N/A");
        String outTime = getActivity().getString(R.string.campaign_check_out_time,"N/A");


        int resourceInId =  R.drawable.ic_status_red;
        int resourceOutId = R.drawable.ic_status_red;


        if(statusModel != null){
            inTime = getActivity().getString(R.string.campaign_check_in_time,
                    statusModel.getCheckInTime() == null ? "N/A" :   Util.formatTime(statusModel.getCheckInTime()));
            outTime = getActivity().getString(R.string.campaign_check_out_time,
                    statusModel.getCheckOutTime() == null ? "N/A" :   Util.formatTime(statusModel.getCheckOutTime()));

             resourceInId =  statusModel.getCheckInStatus() == 1 ? R.drawable.ic_status_green : R.drawable.ic_status_red;
             resourceOutId = statusModel.getCheckOutStatus() == 1 ? R.drawable.ic_status_green : R.drawable.ic_status_red;

        }

        imgInIndicator.setImageResource(resourceInId);
        imgOutIndicator.setImageResource(resourceOutId);
        tvInTime.setText(inTime);
        tvOutTime.setText(outTime);
    }

    public abstract void noContent(boolean hasContent);
}
