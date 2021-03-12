package com.doosy.megaworxx.ui.filter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.CampaignAdapter;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.model.DataList;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.TodayCampaignModel;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.Util;
import com.doosy.megaworxx.viewmodel.CampaignViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FilterFragment extends BaseFragment implements View.OnClickListener {
    private View cardCampaign;
    private FilterViewModel filterViewModel;
    private EditText editStartDate;
    private EditText editEndDate;
    private Button btnFilterCampaign;

    private CampaignViewModel mCampaignViewModel;
    private LiveData<DataServerResponse<CampaignModel>> mResponse;

    private List<CampaignModel> mCampaignModels = new ArrayList<>();

    private RecyclerView mRecyclerviewCampaign;
    private CampaignAdapter mCampaignAdapter;
    private boolean isLoading = false;

    public FilterFragment (){

    }

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();

        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mCampaignViewModel = ViewModelProviders.of(this).get(CampaignViewModel.class);
        View root = inflater.inflate(R.layout.fragment_filter, container, false);

      /*  final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_filter;
    }

    private void openDatePicker(final EditText editText){
        final Calendar c = Calendar.getInstance();
        final DatePickerDialog datePickerDialog;
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String dateOfBirth = ((dayOfMonth<10)?"0"+dayOfMonth : dayOfMonth) +"/"+ (((month)<10)?"0"+month : month) +"/"+year;
                        editText.setText(dateOfBirth);
                    }
                },mYear,mMonth,mDay);
        datePickerDialog.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(editStartDate);

            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(editEndDate);

            }
        });

        btnFilterCampaign.setOnClickListener(this);
    }

    private void initViews(View view){
        editStartDate = view.findViewById(R.id.etStartDate);
        editEndDate = view.findViewById(R.id.etEndDate);
        mRecyclerviewCampaign = view.findViewById(R.id.recyclerViewCampaign);
        btnFilterCampaign = view.findViewById(R.id.btnFilterCampaign);
    }

    private void initRecyclerView(){

        mRecyclerviewCampaign.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mCampaignAdapter = new CampaignAdapter(mCampaignModels,getActivity());
        mRecyclerviewCampaign.setAdapter(mCampaignAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnFilterCampaign.getId()){
            mCampaignViewModel.fetchTodayCampaign("", getModel());
            subscribe();
        }
    }

    private TodayCampaignModel getModel(){
        TodayCampaignModel model = new
                TodayCampaignModel(setting.getUserId(), Util.convertToFormat(setting.getTodayDate()));
        return model;
    }

    private void subscribe() {

        mResponse = mCampaignViewModel.getTodayCampaignResponse();

        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<CampaignModel>>() {
            @Override
            public void onChanged(DataServerResponse<CampaignModel> response) {

                mResponse.removeObserver(this);
                if(mResponse.hasActiveObservers()){
                    mResponse.removeObservers(getActivity());
                }

                if(response == null){
                    Log.d(Constants.TAG, "Null");
                    return;
                }

                Log.d(Constants.TAG, "Before successfully");

                if(response.isSuccessful()){
                    mCampaignModels = response.getDataList();
                    initRecyclerView();
                }

            }
        });
    }

    private void fetchData(){
        mCampaignModels = DataList.getCampaigns();
        initRecyclerView();
        isLoading = false;
        btnFilterCampaign.setEnabled(!isLoading);
    }
}