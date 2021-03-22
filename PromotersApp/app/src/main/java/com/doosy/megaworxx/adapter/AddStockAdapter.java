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

public class AddStockAdapter extends RecyclerView.Adapter<AddStockAdapter.ViewHolder> {
    private List<AddStockModel> addStockModels = new ArrayList<>();
    private Context mContext;

    public AddStockAdapter(List<AddStockModel> addStockModels, Context activity) {
        mContext = activity;
        this.addStockModels = addStockModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.add_stock_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AddStockModel model = addStockModels.get(holder.getAdapterPosition());

        holder.tvItemName.setText(model.getItemName());
        //holder.qtyEditTextListener.updatePosition(holder.getAdapterPosition());
        //holder.edQty.setText(String.valueOf(addStockModels.get(holder.getAdapterPosition()).getQuantity()));

    }

    private void updateQty(int position, int qty){
        ((AddStockActivity)mContext).updateQty(position, qty);
    }

    @Override
    public int getItemCount() {
        return addStockModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvItemName;
        private EditText edQty;
        //private QtyEditTextListener qtyEditTextListener;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            edQty = itemView.findViewById(R.id.edQty);
            this.edQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s == null) return;
                    if(s.toString().isEmpty()) return;

                    try{
                        int number = Integer.parseInt(s.toString());
                        if (number == (int)number)
                        {
                            updateQty(getAdapterPosition(), number);
                        }
                    }catch (Exception ex){
                        edQty.setText(null);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        @Override
        public void onClick(View view) {

        }
    }

    private class QtyEditTextListener implements TextWatcher{

        private int position;

        public void updatePosition(int position){
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().isEmpty()) return;

            try{
                int number = Integer.parseInt(s.toString());
                if (number == (int)number)
                {
                    Log.d(Constants.TAG, "On if before update: " + number);
                    // Number is integer
                    //updateQty(position, number);
                }
                Log.d(Constants.TAG, "On Try: " + number);
            }catch (Exception ex){
                Log.d(Constants.TAG, "Failed to convert QTY: " + ex.getMessage());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
