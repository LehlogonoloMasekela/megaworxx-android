package com.doosy.megaworxx.ui.sales;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.ItemsAdapter;
import com.doosy.megaworxx.adapter.SpinnerAdapter;
import com.doosy.megaworxx.adapter.StockAdapter;
import com.doosy.megaworxx.entity.Stock;
import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.viewmodel.ItemViewModel;

public class AddSalesActivity extends BaseActivity {

    private Spinner spinner;
    private ItemViewModel itemViewModel;
    private LiveData<DataServerResponse<StockItem>> mDataResponse;
    private Button btnCancel, btnSave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sales);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        initViews();
        itemViewModel.fetchItems(settings.getToken());
        mDataResponse = itemViewModel.getDataResponse();

        mDataResponse.observe(this, new Observer<DataServerResponse<StockItem>>() {
            @Override
            public void onChanged(DataServerResponse<StockItem> response) {
                if(response != null && response.isSuccessful()){
                    SpinnerAdapter adapter = new SpinnerAdapter( AddSalesActivity.this, response.getDataList());
                    spinner.setAdapter(adapter);
                }
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
                finish();
            }
        });
    }

    private void initViews(){
        spinner = findViewById(R.id.spinner);
    }
}
