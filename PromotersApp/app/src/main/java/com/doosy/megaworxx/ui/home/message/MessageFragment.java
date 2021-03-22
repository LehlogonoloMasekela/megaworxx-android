package com.doosy.megaworxx.ui.home.message;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

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
    private FrameLayout noContent;

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

    }

    @Override
    public void retryLoad() {
        loadData();
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
            loadData();
        }
    }

    @Override
    public void noContent(boolean hasContent){
        mRecyclerviewMessage.setVisibility(hasContent ? View.VISIBLE : View.GONE);
        noContent.setVisibility(hasContent ? View.GONE : View.VISIBLE);
    }


    private void loadData(){
        showLoading();
        messageViewModel.fetchMessages(setting.getToken(), setting.getUserId());
        mResponse = messageViewModel.getDataResponse();
        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<Message>>() {
            @Override
            public void onChanged(DataServerResponse<Message> response) {
                hideLoading();
                if(response == null){
                    showErrorPage();
                    return;
                }

                if(response != null && response.isSuccessful()){
                    if(response.getDataList().size() > 0){
                        mMessages = response.getDataList();
                        initRecyclerView();
                        noContent(true);

                    }else{
                        noContent(false);
                    }
                }

            }
        });
    }

    private void initViews(View view){
        mRecyclerviewMessage = view.findViewById(R.id.recyclerViewMessages);
        noContent = view.findViewById(R.id.noContent);
    }

    private void initRecyclerView(){

        mRecyclerviewMessage.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mMessageAdapter = new MessageAdapter(mMessages, getActivity());
        mRecyclerviewMessage.setAdapter(mMessageAdapter);
    }
}