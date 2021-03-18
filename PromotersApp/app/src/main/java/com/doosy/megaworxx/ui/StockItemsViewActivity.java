package com.doosy.megaworxx.ui;

import android.os.Bundle;

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
import com.doosy.megaworxx.util.QuestionnaireType;
import com.doosy.megaworxx.viewmodel.StockItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class StockItemsViewActivity extends BaseActivity {

    private StockItemViewModel stockItemViewModel;
    private LiveData<DataServerResponse<StockSaleBase>> mStockResponse;
    private LiveData<DataServerResponse<StockSaleBase>> mSaleResponse;


    private List<StockSaleBase> stockSaleBases = new ArrayList<>();
    private StockItemViewAdapter stockItemViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        stockItemViewModel = new ViewModelProvider(this).get(StockItemViewModel.class);

        String typeStr = getString(R.string.key_campaign_page_type);
        String dataTpeStr = getString(R.string.key_campaign_stock_sale_data);

        if(getIntent().hasExtra(typeStr) && getIntent().hasExtra(dataTpeStr)) {
            showLoading();
            QuestionnaireType type = (QuestionnaireType) getIntent().getExtras().getSerializable(typeStr);
            StockSaleBase stockSaleBase;

            if(type == QuestionnaireType.Stock){
                 stockSaleBase = (StockSaleBase) getIntent().getExtras().getSerializable(dataTpeStr);
                 stockItemViewModel.fetchPromoterStock(settings.getToken(), stockSaleBase.getId() );
                 getStock();
            }else if(type == QuestionnaireType.Sales){
                 stockSaleBase = (StockSaleBase) getIntent().getExtras().getSerializable(dataTpeStr);
                stockItemViewModel.fetchPromoterSale(settings.getToken(), stockSaleBase.getId() );
                getSales();
            }

        }
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
                        initView();
                    }else{
                        //No data here
                    }
                }
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
                        initView();
                    }else{
                        //No data here
                    }
                }
            }
        });
    }

    private void initView(){
       RecyclerView recyclerViewStockView = findViewById(R.id.recyclerViewStockView);
        recyclerViewStockView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        stockItemViewAdapter = new StockItemViewAdapter(stockSaleBases, this);
        recyclerViewStockView.setAdapter(stockItemViewAdapter);
    }
}
