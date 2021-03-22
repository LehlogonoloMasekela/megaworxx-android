package com.doosy.megaworxx.ui.home.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.doosy.megaworxx.HomeActivity;
import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.Message;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.viewmodel.MessageViewModel;

public class MessageActivity extends BaseActivity implements View.OnClickListener {
    private MessageViewModel messageViewModel;
    private LiveData<DataServerResponse<Message>> mResponse;
    private TextView tvMessage;
    private TextView tvDate;
    private String messageId;
    private boolean canOpenHome = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        initViews();
        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);

        showLoading();
        if(getIntent().hasExtra("id")) {
            showLoading();
            messageId = getIntent().getExtras().getString("id");

            loadData();
        }

        if(getIntent().hasExtra("canOpenHome")) {
            canOpenHome = getIntent().getExtras().getBoolean("canOpenHome");
        }
        findViewById(R.id.toolbar).setOnClickListener(this);

    }

    @Override
    public void displayPage(boolean hasContent) {

    }

    @Override
    public void retryLoad() {
        loadData();
    }

    private void loadData(){
        messageViewModel.getMessageById(settings.getToken(), messageId);
        mResponse = messageViewModel.getDataResponse();
        mResponse.observe(this, new Observer<DataServerResponse<Message>>() {
            @Override
            public void onChanged(DataServerResponse<Message> response) {
                hideLoading();
                if(response != null && response.isSuccessful()){
                    if(response.getDataList().size() > 0){
                        Message message = response.getDataList().get(0);
                        tvDate.setText(message.getDate());
                        tvMessage.setText(message.getMessage());
                        return;
                    }
                }

                displayErrorPage();
            }
        });
    }

    private void initViews(){
        tvMessage = findViewById(R.id.tvMessage);
        tvDate = findViewById(R.id.tvDate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!canOpenHome) return;
        Intent mainIntent = new Intent(this,
                HomeActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.toolbar){
            super.onBackPressed();
        }
    }
}
