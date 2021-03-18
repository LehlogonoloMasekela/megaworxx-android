package com.doosy.megaworxx.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.model.AddStockModel;
import com.doosy.megaworxx.ui.campaign.CampaignActivity;
import com.doosy.megaworxx.ui.stock.AddStockActivity;
import com.doosy.megaworxx.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class AddStockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AddStockModel> addStockModels = new ArrayList<>();
    private Context mContext;

    public AddStockAdapter(List<AddStockModel> addStockModels, Context activity) {
        mContext = activity;
        this.addStockModels = addStockModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.add_stock_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final AddStockModel model = addStockModels.get(position);

        ((ViewHolder)holder).tvItemName.setText(model.getItemName());
        ((ViewHolder)holder).edQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()) return;

                try{
                    int number = Integer.parseInt(s.toString());
                    if (number == (int)number)
                    {
                        updateQty(position, number);
                        // Number is integer
                    }
                }catch (Exception ex){
                    ((ViewHolder)holder).edQty.setText("0");
                }

            }
        });

    }

    private void updateQty(int position, int qty){
        ((AddStockActivity)mContext).updateQty(position, qty);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return addStockModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvItemName;
        private EditText edQty;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            edQty = itemView.findViewById(R.id.edQty);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
