package com.doosy.megaworxx.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.FormQuestions;
import com.doosy.megaworxx.ui.feedback.AddQuestionnaireActivity;
import com.doosy.megaworxx.ui.stock.AddStockActivity;
import com.doosy.megaworxx.util.Util;

import java.util.ArrayList;
import java.util.List;


public class AddQuestionaireAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<FormQuestions> mQuestions = new ArrayList<>();
    private Context mContext;

    private enum QuestionType{
        SingleInput,
        TrueOrFalse,
        MultipleOption
    }

    public AddQuestionaireAdapter(List<FormQuestions> questions, Context activity) {
        mContext = activity;
        this.mQuestions = questions;
    }

    private QuestionType getQuestionType(String type){

        switch (type){

            case Util.QuestionTypeSingle:
                return QuestionType.SingleInput;
            case Util.QuestionTypeTrueOrFalse:
                return QuestionType.TrueOrFalse;
            case Util.QuestionTypeMultipleOption:
                return QuestionType.MultipleOption;
        }

        return QuestionType.SingleInput;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_question, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final FormQuestions question = mQuestions.get(position);

        TextView tvQuestion;
        View view;

        if(getQuestionType(question.getFormQuestionTypeId()) == QuestionType.SingleInput){
            //Do single question here
            view = ((View)holder.itemView).findViewById(R.id.itemQuestionSingle);
            EditText etAnswer = view.findViewById(R.id.etQuestionAnswer);
            etAnswer.setTag(position);
            tvQuestion = view.findViewById(R.id.tvQuestion);
            tvQuestion.setText(question.getQuestion());
            etAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    setAnswer(position, s.toString());
                }
            });
            view.setVisibility(View.VISIBLE);
        }else if(getQuestionType(question.getFormQuestionTypeId()) == QuestionType.TrueOrFalse){
            view = ((View)holder.itemView).findViewById(R.id.itemQuestionDouble);
            final RadioGroup radioGroup = view.findViewById(R.id.itemDoubleQuestionOptions);
            radioGroup.setTag(position);
            tvQuestion = view.findViewById(R.id.tvQuestion);
            tvQuestion.setText(question.getQuestion());
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                   RadioButton selectedRadioButton = (RadioButton)radioGroup.findViewById(checkedId);
                   setAnswer(position, selectedRadioButton.getText().toString());
                }
            });
            view.setVisibility(View.VISIBLE);
        }else if(getQuestionType(question.getFormQuestionTypeId()) == QuestionType.MultipleOption){
            view = ((View)holder.itemView).findViewById(R.id.itemQuestionMultiple);

            tvQuestion = view.findViewById(R.id.tvQuestion);
            tvQuestion.setText(question.getQuestion());

            final RadioGroup radioGroup = view.findViewById(R.id.itemMultipleQuestionOptions);
            radioGroup.setTag(position);
            radioGroup.setOrientation(RadioGroup.VERTICAL);
            final RadioButton[] radioButtons = new RadioButton[question.getFormQuestionOptions().size()];
            for (int i = 0; i < question.getFormQuestionOptions().size(); i++) {
                RadioButton radio = new RadioButton(mContext);
                radio.setText(question.getFormQuestionOptions().get(i).getName());
                radio.setId(i+1);
                radioGroup.addView(radio);
            }
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton selectedRadioButton = (RadioButton)radioGroup.findViewById(checkedId);
                    setAnswer(position, selectedRadioButton.getText().toString());
                }
            });
            view.setVisibility(View.VISIBLE);
        }

    }

    private CampaignModel getCampaignModel(){
        CampaignModel campaignModel;
        if(mContext instanceof AddQuestionnaireActivity){
            //campaignModel = ((AddFeedbackActivity)mContext).getCam
        }

        return null;
    }

    private void setAnswer(int position, String answer){

        if(mContext instanceof AddQuestionnaireActivity){
            ((AddQuestionnaireActivity)mContext).setAnswer(position, answer);
        }
    }

    private void updateQty(int position, int qty){
        ((AddStockActivity)mContext).updateQty(position, qty);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvQuestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
