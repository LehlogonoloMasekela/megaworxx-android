package com.doosy.megaworxx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.StockItem;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<StockItem> {
    public SpinnerAdapter(@NonNull Context context, @NonNull List<StockItem> countries) {
        super(context,0, countries);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return iniView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return iniView(position, convertView, parent);
    }

    private View iniView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.stockitem_item, parent, false);
        }

        TextView tvCountry = convertView.findViewById(R.id.tvItemName);

        StockItem country = getItem(position);

        if(country != null){

            tvCountry.setText(country.getName());
        }

        return convertView;
    }
}
