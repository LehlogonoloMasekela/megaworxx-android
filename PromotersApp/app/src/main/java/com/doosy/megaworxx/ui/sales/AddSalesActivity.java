package com.doosy.megaworxx.ui.sales;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.SpinnerAdapter;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.SaleModel;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.viewmodel.ItemViewModel;
import com.doosy.megaworxx.viewmodel.SaleViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class AddSalesActivity extends BaseActivity {

    private Spinner spinner;
    private ItemViewModel itemViewModel;
    private LiveData<DataServerResponse<StockItem>> mDataResponse;
    private Button btnCancel, btnSave;
    private Campaign mCampaign;
    private CampaignModel mCampaignModel;

    private SaleViewModel mSaleViewModel;
    private LiveData<ServerResponse> mResponse;

    private EditText etDeviceId;
    private EditText etPrice;
    private String mSelectedStockItemId;

    private List<StockItem> mStockItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);
        mSaleViewModel = new ViewModelProvider(this).get(SaleViewModel.class);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        initViews();

        String keyCampaign = getString(R.string.key_campaign);
        String keyCampaignModel = getString(R.string.key_campaign_model);

        if(getIntent().hasExtra(keyCampaign) && getIntent().hasExtra(keyCampaignModel)) {
            mCampaign = (Campaign) getIntent().getSerializableExtra(keyCampaign);
            mCampaignModel = (CampaignModel) getIntent().getSerializableExtra(keyCampaignModel);
            MaterialToolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(mCampaignModel.getCampaignName());
        }

        itemViewModel.fetchStockItems(settings.getToken());
        mDataResponse = itemViewModel.getDataResponse();

        mDataResponse.observe(this, new Observer<DataServerResponse<StockItem>>() {
            @Override
            public void onChanged(DataServerResponse<StockItem> response) {
                if(response != null && response.isSuccessful()){
                    mStockItems = response.getDataList();
                    initSpinner();
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectedStockItemId = mStockItems.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceId = etDeviceId.getText().toString();
                String price = etPrice.getText().toString();

                //Validation here
                SaleModel model = new SaleModel(mCampaignModel.getPromoterId(), mCampaignModel.getCampaignDateId(),
                        mCampaignModel.getCampaignId(), mCampaignModel.getCampaignLocationId(), settings.getsCoordinates(),
                        mSelectedStockItemId, deviceId, Double.parseDouble(price));

                showLoading();
                saveSale(model);
            }
        });
    }

    private void initSpinner() {
        SpinnerAdapter adapter = new SpinnerAdapter( AddSalesActivity.this, mStockItems);
        spinner.setAdapter(adapter);
    }

    private void saveSale(SaleModel model){

        mSaleViewModel.saveCampaignSale(settings.getToken(), model);
        mResponse = mSaleViewModel.getResponse();
        mResponse.observe(this, new Observer<ServerResponse>() {
            @Override
            public void onChanged(ServerResponse response) {
                hideLoading();

                if(response == null){
                    showError("Error occurred, please try again later.");
                    return;
                }

                if(!response.isSuccessful()){

                    StringBuilder strBuilderMsg = new StringBuilder();
                    String message = "Error occurred, please try again later.";
                    strBuilderMsg.append(message);

                    if(response.getMessages().size() >= 1){
                        strBuilderMsg = new StringBuilder();
                        for (String msg: response.getMessages()
                        ) {
                            strBuilderMsg.append(msg);
                            strBuilderMsg.append(System.getProperty("line.separator"));
                        }
                    }

                    showError(strBuilderMsg.toString());
                    return;
                }

                if(response.isSuccessful()){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isSuccessful", true);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private void initViews(){
        etDeviceId = findViewById(R.id.etDeviceId);
        etPrice = findViewById(R.id.etPrice);
        spinner = findViewById(R.id.spinner);
    }
}
