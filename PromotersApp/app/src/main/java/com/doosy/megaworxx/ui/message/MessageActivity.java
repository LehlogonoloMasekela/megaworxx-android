package com.doosy.megaworxx.ui.message;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Message;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.viewmodel.MessageViewModel;

public class MessageActivity extends BaseActivity {
    private MessageViewModel messageViewModel;
    private LiveData<DataServerResponse<Message>> mResponse;

    private TextView tvMessage;
    private TextView tvDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        initViews();

        if(getIntent().hasExtra("id")) {
            showLoading();
            String messageId = getIntent().getExtras().getString("id");
            messageViewModel.getMessageById(settings.getToken(), messageId);
            subscribe();
        }

    }

    private void subscribe(){

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

                    }else{
                        //No data here
                    }
                }
            }
        });
    }

    private void initViews(){
        tvMessage = findViewById(R.id.tvMessage);
        tvDate = findViewById(R.id.tvDate);
    }
}
