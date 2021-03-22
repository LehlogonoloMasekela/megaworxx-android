package com.doosy.megaworxx.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.StockItemViewAdapter;
import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.QuestionnaireType;
import com.doosy.megaworxx.util.Util;
import com.doosy.megaworxx.viewmodel.StockItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class StockItemsViewActivity extends BaseActivity {

    private StockItemViewModel stockItemViewModel;
    private LiveData<DataServerResponse<StockSaleBase>> mStockResponse;
    private LiveData<DataServerResponse<StockSaleBase>> mSaleResponse;
    private RecyclerView mRecyclerViewStockView;
    private FrameLayout noContent;
    private List<StockSaleBase> stockSaleBases = new ArrayList<>();
    private StockItemViewAdapter stockItemViewAdapter;
    private QuestionnaireType type;
    private String typeStr;
    private String dataTpeStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        stockItemViewModel = new ViewModelProvider(this).get(StockItemViewModel.class);
        initViews();
        showLoading();
        typeStr = getString(R.string.key_campaign_page_type);
        dataTpeStr = getString(R.string.key_campaign_stock_sale_data);

        if(getIntent().hasExtra(typeStr) && getIntent().hasExtra(dataTpeStr)) {
            type = (QuestionnaireType) getIntent().getExtras().getSerializable(typeStr);
            loadData();
        }
    }

    private void loadData(){
        StockSaleBase stockSaleBase = null;

        if(type == QuestionnaireType.Stock){
            ((TextView)findViewById(R.id.tvItemQtyPrice)).setText("Qty");
            stockSaleBase = (StockSaleBase) getIntent().getExtras().getSerializable(dataTpeStr);
            stockItemViewModel.fetchPromoterStock(settings.getToken(), stockSaleBase.getId() );
            getStock();
        }else if(type == QuestionnaireType.Sales){
            ((TextView)findViewById(R.id.tvItemQtyPrice)).setText("Price");
            stockSaleBase = (StockSaleBase) getIntent().getExtras().getSerializable(dataTpeStr);
            stockItemViewModel.fetchPromoterSale(settings.getToken(), stockSaleBase.getId() );
            getSales();
        }

        if(stockSaleBase != null)
            ((TextView)findViewById(R.id.topHeaderDate)).setText(getString(R.string.campaign_date,
                    Util.formatDate(stockSaleBase.getDateCreated()) ));
    }

    private void initViews(){
        mRecyclerViewStockView = findViewById(R.id.recyclerViewStockView);
        noContent = findViewById(R.id.noContent);
    }

    @Override
    public void displayPage(boolean hasContent) {
        mRecyclerViewStockView.setVisibility(hasContent ? View.VISIBLE : View.GONE);
        noContent.setVisibility(hasContent ? View.GONE : View.VISIBLE);
    }


    @Override
    public void retryLoad() {
        loadData();
    }

    private void getStock(){
        showLoading();
        mStockResponse = stockItemViewModel.getStockResponse();

        mStockResponse.observe(this, new Observer<DataServerResponse<StockSaleBase>>() {
            @Override
            public void onChanged(DataServerResponse<StockSaleBase> response) {
                hideLoading();
                if(response != null && response.isSuccessful()){
                    if(response.getDataList().size() > 0){
                        stockSaleBases.addAll(response.getDataList());
                        initViewRecyclerView(1);
                        Log.d(Constants.TAG, "Display on success 1");
                        displayPage(true);
                    }else{
                        //No data here
                        displayPage(false);
                        Log.d(Constants.TAG, "Display on noc content 1");
                    }

                    return;
                }

                displayErrorPage();
            }
        });
    }

    private void getSales(){
        showLoading();
        mSaleResponse = stockItemViewModel.getStockResponse();

        mSaleResponse.observe(this, new Observer<DataServerResponse<StockSaleBase>>() {
            @Override
            public void onChanged(DataServerResponse<StockSaleBase> response) {
                hideLoading();
                if(response != null && response.isSuccessful()){
                    if(response.getDataList().size() > 0){
                        stockSaleBases.addAll(response.getDataList());
                        initViewRecyclerView(2);
                        displayPage(true);
                        Log.d(Constants.TAG, "Display on success 2");
                    }else{
                        //No data here
                        displayPage(false);
                        Log.d(Constants.TAG, "Display on no content 1");
                    }

                    return;
                }

                displayErrorPage();
            }
        });
    }

    private void initViewRecyclerView(int type){

        mRecyclerViewStockView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        stockItemViewAdapter = new StockItemViewAdapter(stockSaleBases, this, type);
        mRecyclerViewStockView.setAdapter(stockItemViewAdapter);
    }
}
