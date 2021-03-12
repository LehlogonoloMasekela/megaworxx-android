package com.doosy.megaworxx.ui.stock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.AddStockAdapter;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Stock;
import com.doosy.megaworxx.model.AddStockModel;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.viewmodel.StockViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class AddStockActivity extends BaseActivity implements View.OnClickListener {

    private StockViewModel stockViewModel;
    private LiveData<DataServerResponse<Stock>> mDataResponse;
    private LiveData<ServerResponse> mResponse;
    private Campaign mCampaign;
    private CampaignModel mCampaignModel;
    private List<Stock> mStocks;
    private List<AddStockModel> mAddStockModels;

    private RecyclerView recyclerViewStock;
    private AddStockAdapter mAddStockAdapter;
    private Button btnSave, btnCancel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        stockViewModel = new ViewModelProvider(this).get(StockViewModel.class);
        initViews();

        String keyCampaign = getString(R.string.key_campaign);
        String keyCampaignModel = getString(R.string.key_campaign_model);

        if(getIntent().hasExtra(keyCampaign) && getIntent().hasExtra(keyCampaignModel)) {
            mCampaign = (Campaign) getIntent().getSerializableExtra(keyCampaign);
            mCampaignModel = (CampaignModel) getIntent().getSerializableExtra(keyCampaignModel);
            MaterialToolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle(mCampaignModel.getCampaignName());
            stockViewModel.fetchCampaignStock(settings.getToken(), mCampaign.getId());
            mDataResponse = stockViewModel.getCampaignStock();

            mDataResponse.observe(this, new Observer<DataServerResponse<Stock>>() {
                @Override
                public void onChanged(DataServerResponse<Stock> response) {
                    if(response != null && response.isSuccessful()){
                        mStocks = response.getDataList();
                        toAddStockModel();
                    }
                }
            });
        }


        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void initViews(){
        recyclerViewStock = findViewById(R.id.recyclerViewStock);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void toAddStockModel(){
        if(mStocks == null) return;
        mAddStockModels = new ArrayList<>();
        for (Stock stock: mStocks) {

            AddStockModel model = new AddStockModel(mCampaignModel.getPromoterId(),
                    mCampaignModel.getCampaignDateId(), mCampaign.getId(),
                    mCampaignModel.getCampaignLocationId(), settings.getsCoordinates(),
                    stock.getStockItemId(),stock.getStockItem().getName());
            mAddStockModels.add(model);
        }
        initRecyclerView();
    }

    private void initRecyclerView(){

        recyclerViewStock.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mAddStockAdapter = new AddStockAdapter(mAddStockModels,this);
        recyclerViewStock.setAdapter(mAddStockAdapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnCancel){
            finish();
        }else if(v.getId() == R.id.btnSave){
            showLoading();
            saveStock();
        }
    }

    public void updateQty(int position, int qty){
        mAddStockModels.get(position).setQuantity(qty);
    }

    private void saveStock(){

        stockViewModel.addStock(settings.getToken(), mAddStockModels);
        mResponse = stockViewModel.getResponse();
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
}
