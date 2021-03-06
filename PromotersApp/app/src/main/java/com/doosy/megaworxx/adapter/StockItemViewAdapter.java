package com.doosy.megaworxx.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.entity.TrimmedFormAnswer;
import com.doosy.megaworxx.util.Constants;

import java.util.ArrayList;
import java.util.List;


public class StockItemViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<StockSaleBase> mStockSaleBases = new ArrayList<>();
    private Context mContext;
    private int mType;

    public StockItemViewAdapter(List<StockSaleBase> stockSaleBases, Context activity, int type) {
        mContext = activity;
        mStockSaleBases = stockSaleBases;
        mType= type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_stock_item_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final StockSaleBase model = mStockSaleBases.get(position);

        String description = model.getStockItem() == null ? "No Description" : model.getStockItem().getName();
        String name = model.getStockItem() == null ?  "No name" :
                (model.getStockItem().getMake() == null) ? "No name" : model.getStockItem().getMake().getName();

        ((ViewHolder)holder).tvItemName.setText(name);
        ((ViewHolder)holder).tvItemDesc.setText(description);

        if(mType == 1){
            ((ViewHolder)holder).tvQty.setText(String.valueOf(model.getQuantity()));
        }else if(mType == 2){
            ((ViewHolder)holder).tvQty.setText(String.valueOf(model.getPrice()));
            Log.d(Constants.TAG, "Price: " + model.getPrice());
        }

    }

    @Override
    public int getItemCount() {
        return mStockSaleBases.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvItemName;
        private TextView tvItemDesc;
        private TextView tvQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemDesc = itemView.findViewById(R.id.tvItemDesc);
            tvQty = itemView.findViewById(R.id.tvQty);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
