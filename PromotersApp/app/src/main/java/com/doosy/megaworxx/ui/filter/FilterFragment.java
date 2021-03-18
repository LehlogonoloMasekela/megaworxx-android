package com.doosy.megaworxx.ui.filter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.CampaignAdapter;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.model.CampaignFilterModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.ui.BaseFragment;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.viewmodel.CampaignViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FilterFragment extends BaseFragment implements View.OnClickListener {
    private View cardCampaign;
    private EditText editStartDate;
    private EditText editEndDate;
    private Button btnFilterCampaign;

    private CampaignViewModel mCampaignViewModel;
    private LiveData<DataServerResponse<CampaignModel>> mResponse;

    private List<CampaignModel> mCampaignModels2 = new ArrayList<>();

    private RecyclerView mRecyclerviewCampaign;
    private CampaignAdapter mCampaignAdapter;
    private boolean isLoading = false;

    public FilterFragment (){

    }

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCampaignViewModel = new ViewModelProvider(this).get(CampaignViewModel.class);
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
                        String dateOfBirth = year+"-"+ (((month)<10)?"0"+month : month) +"-"+ ((dayOfMonth<10)?"0"+dayOfMonth : dayOfMonth) ;
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

    private void initRecyclerView(List<CampaignModel> campaigns){

        mRecyclerviewCampaign.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        mCampaignAdapter = new CampaignAdapter(campaigns, getActivity());
        Log.d(Constants.TAG, "Item Count: " + mCampaignAdapter.getItemCount());
        mRecyclerviewCampaign.setAdapter(mCampaignAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnFilterCampaign.getId()){
            String startDate = editStartDate.getText().toString();
            String endDate = editEndDate.getText().toString();

            if(startDate == null || startDate.isEmpty()) {
                Toast.makeText(getActivity(), "Start date is required.", Toast.LENGTH_SHORT).show();
                return;
            }

            if(endDate == null || endDate.isEmpty()) {
                Toast.makeText(getActivity(), "End date is required.", Toast.LENGTH_SHORT).show();
                return;
            }

            CampaignFilterModel model = new CampaignFilterModel(
                    setting.getUserId(),
                    startDate,
                    endDate
            );

            Log.d(Constants.TAG, model.toString());

            showLoading();
            mCampaignViewModel.fetchFilteredCampaign( setting.getToken(), model);
            subscribe();
        }
    }

    private void subscribe() {

        mResponse = mCampaignViewModel.getDataResponse();

        mResponse.observe(getViewLifecycleOwner(), new Observer<DataServerResponse<CampaignModel>>() {
            @Override
            public void onChanged(DataServerResponse<CampaignModel> response) {
                hideLoading();
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
                    initRecyclerView(response.getDataList());
                }

            }
        });
    }

}