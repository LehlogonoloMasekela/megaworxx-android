package com.doosy.megaworxx.ui.message;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.MessageAdapter;
import com.doosy.megaworxx.entity.Message;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.viewmodel.MessageViewModel;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends BaseFragment {

    private MessageViewModel messageViewModel;
    private LiveData<DataServerResponse<Message>> mResponse;
    private List<Message> mMessages = new ArrayList<>();

    private RecyclerView mRecyclerviewMessage;
    private MessageAdapter mMessageAdapter;


    public MessageFragment (){

    }

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageViewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        messageViewModel.fetchMessages(setting.getToken(), setting.getUserId());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_message;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if(mResponse != null){
            initRecyclerView();
        }else{
            subscribe();
        }
    }

    private void subscribe(){
        showLoading();
        mResponse = messageViewModel.getDataResponse();

        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<Message>>() {
            @Override
            public void onChanged(DataServerResponse<Message> response) {
                hideLoading();
                if(response != null && response.isSuccessful()){
                    if(response.getDataList().size() > 0){
                        mMessages = response.getDataList();
                        initRecyclerView();
                    }else{
                        //No data here
                    }
                }
            }
        });
    }

    private void initViews(View view){
        mRecyclerviewMessage = view.findViewById(R.id.recyclerViewMessages);
    }

    private void initRecyclerView(){

        mRecyclerviewMessage.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mMessageAdapter = new MessageAdapter(mMessages, getActivity());
        mRecyclerviewMessage.setAdapter(mMessageAdapter);
    }
}