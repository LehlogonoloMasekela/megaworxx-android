package com.doosy.megaworxx.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.ui.campaign.CampaignActivity;

import java.util.ArrayList;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Feedback> mFeedbacks = new ArrayList<>();
    private Context mContext;

    public FeedbackAdapter(List<Feedback> surveys, Context activity) {
        mContext = activity;
        mFeedbacks = surveys;
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
        final Feedback feedback = mFeedbacks.get(position);

        String displayName = mContext.getApplicationContext().getResources().getString(R.string.feedback_item);
        ((ViewHolder)holder).tvDisplayName.setText(displayName);
        ((ViewHolder)holder).imgItemIcon.setImageResource(R.drawable.ic_feedback_icon);
        ((ViewHolder)holder).imgActionIcon.setImageResource(R.drawable.ic_more);

        ((ViewHolder)holder).cardItemMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CampaignActivity)mContext).viewDetail(CampaignActivity.CampaignTab.Feedback);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFeedbacks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardItemMainLayout;
        private ImageView imgItemIcon;
        private ImageView imgActionIcon;
        private TextView tvDisplayName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardItemMainLayout = itemView.findViewById(R.id.cardItemMainLayout);
            imgItemIcon = itemView.findViewById(R.id.campaignIcon);
            imgActionIcon = itemView.findViewById(R.id.campaignMoreIcon);
            tvDisplayName = itemView.findViewById(R.id.campaignName);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
