package com.doosy.megaworxx.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.Survey;
import com.doosy.megaworxx.ui.ViewAnswersActivity;
import com.doosy.megaworxx.util.QuestionnaireType;

import java.util.ArrayList;
import java.util.List;

public class SurveyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Survey> mSurveys = new ArrayList<>();
    private Context mContext;

    public SurveyAdapter(List<Survey> surveys, Context activity) {
        mContext = activity;
        mSurveys = surveys;
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
        final Survey survey = mSurveys.get(position);

        ((ViewHolder)holder).tvDisplayName.setText(survey.getCustomerName());
        ((ViewHolder)holder).imgItemIcon.setImageResource(R.drawable.ic_survey);
        ((ViewHolder)holder).imgActionIcon.setImageResource(R.drawable.ic_more);

        ((ViewHolder)holder).cardItemMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyFormId = mContext.getString(R.string.key_form_id);
                String keyQuestionnaireType = mContext.getString(R.string.key_campaign_page_type);

                Bundle bundle = new Bundle();
                bundle.putSerializable(keyQuestionnaireType, QuestionnaireType.Survey);
                bundle.putString(keyFormId, survey.getId());
                Intent intent = new Intent(mContext, ViewAnswersActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSurveys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgItemIcon;
        private ImageView imgActionIcon;
        private TextView tvDisplayName;
        private CardView cardItemMainLayout;

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
