package com.doosy.megaworxx.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.util.Setting;

public abstract class BaseActivity  extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private FrameLayout frameLayout;
    private ConstraintLayout constraintLayout;

    private TextView txtMessage;
    private LinearLayout errorLayout;

    protected Setting settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        settings = new Setting(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(int layoutResID) {
        constraintLayout = (ConstraintLayout)getLayoutInflater()
                .inflate(R.layout.activity_base, null);
        frameLayout = constraintLayout.findViewById(R.id.activity_content);
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(constraintLayout);

    }

    public void showLoading(boolean isLoading){
        Log.d("masoft", isLoading ? "Yes" : "No");
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.INVISIBLE);
        frameLayout.setVisibility(isLoading ? View.GONE : View.VISIBLE);
    }

    public void showToast(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean isConnected(){
        return  settings.ConnectionType() != Setting.ConnectionType.None;
    }

}
