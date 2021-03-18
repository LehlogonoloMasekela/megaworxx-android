package com.doosy.megaworxx.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.ui.campaign.CampaignActivity;
import com.doosy.megaworxx.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class CampaignAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CampaignModel> mCampaignModels = new ArrayList<>();
    private Context mContext;

    public CampaignAdapter(List<CampaignModel> campaignModels, Context activity) {
        mContext = activity;
        mCampaignModels = campaignModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.campaign_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final CampaignModel campaignModel = mCampaignModels.get(position);

        ((ViewHolder)holder).tvDisplayName.setText(campaignModel.getCampaignName() +" at " + campaignModel.getLocation());
        ((ViewHolder)holder).imgItemIcon.setImageResource(R.drawable.ic_campaign);
        ((ViewHolder)holder).imgActionIcon.setImageResource(R.drawable.ic_more);
        ((ViewHolder)holder).cardItemMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String key = mContext.getResources().getString(R.string.key_campaign_model);
                bundle.putSerializable(key, campaignModel);
                Log.d(Constants.TAG, campaignModel.toString());
                Intent intent = new Intent(mContext, CampaignActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCampaignModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgItemIcon;
        private ImageView imgActionIcon;
        private TextView tvDisplayName;
        private CardView cardItemMainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemIcon = itemView.findViewById(R.id.campaignIcon);
            imgActionIcon = itemView.findViewById(R.id.campaignMoreIcon);
            tvDisplayName = itemView.findViewById(R.id.campaignName);
            cardItemMainLayout = itemView.findViewById(R.id.cardItemMainLayout);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
