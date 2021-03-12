package com.doosy.megaworxx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.entity.Survey;
import com.doosy.megaworxx.ui.campaign.CampaignActivity;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<StockItem> stockItems = new ArrayList<>();
    private Context mContext;

    public ItemsAdapter(List<StockItem> surveys, Context activity) {
        mContext = activity;
        stockItems = surveys;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.stockitem_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final StockItem stockItem = stockItems.get(position);

        ((ViewHolder)holder).tvItemName.setText(stockItem.getName());

    }

    @Override
    public int getItemCount() {
        return stockItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvItemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
