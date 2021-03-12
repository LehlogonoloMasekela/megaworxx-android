package com.doosy.megaworxx.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.util.Setting;
import com.doosy.megaworxx.util.TokenTimer;

public abstract class BaseFragment extends Fragment {
    private ConstraintLayout constraintLayout;
    private FrameLayout fragmentContent;
    private ProgressBar mProgressBar;

    private LinearLayout successLayout;
    private TextView tvSuccessMessage;
    private LinearLayout errorLayout;
    private TextView tvErrorMessage;
    private View rootView;
    public Setting setting;

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


        rootView = inflater.inflate(R.layout.fragment_base, parent, false);

        initView(rootView);
        LayoutInflater.from(requireContext()).inflate(resourceId, fragmentContent, true);
        return rootView;
    }

    private void initView(View rootView){
        mProgressBar = rootView.findViewById(R.id.progress_bar);
        fragmentContent = rootView.findViewById(R.id.fragment_content);

        successLayout = rootView.findViewById(R.id.successLayout);
        tvSuccessMessage = rootView.findViewById(R.id.tvSuccessMessage);
        errorLayout = rootView.findViewById(R.id.errorLayout);
        tvErrorMessage = rootView.findViewById(R.id.tvErrorMessage);
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
        fragmentContent.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading(){
        mProgressBar.setVisibility(View.GONE);
        fragmentContent.setVisibility(View.VISIBLE);
    }

    public abstract int getLayoutRes();

}
