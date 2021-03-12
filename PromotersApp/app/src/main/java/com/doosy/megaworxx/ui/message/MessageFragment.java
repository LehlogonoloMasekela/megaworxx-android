package com.doosy.megaworxx.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.FeedbackAdapter;
import com.doosy.megaworxx.adapter.MessageAdapter;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.entity.Message;
import com.doosy.megaworxx.model.DataList;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.ui.filter.FilterFragment;
import com.doosy.megaworxx.ui.profile.ProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends BaseFragment {

    private ProfileViewModel profileViewModel;

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
    public int getLayoutRes() {
        return R.layout.fragment_message;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView(view);
    }

    private void initViews(View view){
        mRecyclerviewMessage = view.findViewById(R.id.recyclerViewMessages);
    }

    private void initRecyclerView(View view){

        mRecyclerviewMessage.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mMessageAdapter = new MessageAdapter(DataList.getMessages(),getActivity());
        mRecyclerviewMessage.setAdapter(mMessageAdapter);
    }
}