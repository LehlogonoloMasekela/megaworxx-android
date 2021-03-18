package com.doosy.megaworxx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.doosy.megaworxx.R;
import com.doosy.megaworxx.entity.TrimmedFormAnswer;
import java.util.ArrayList;
import java.util.List;


public class ViewQuestionnaireAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TrimmedFormAnswer> mTrimmedFormAnswers = new ArrayList<>();
    private Context mContext;

    public ViewQuestionnaireAdapter(List<TrimmedFormAnswer> trimmedFormAnswers, Context activity) {
        mContext = activity;
        mTrimmedFormAnswers = trimmedFormAnswers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_question_answers, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final TrimmedFormAnswer question = mTrimmedFormAnswers.get(position);

        ((ViewHolder)holder).tvQuestion.setText(question.getQuestion());
        ((ViewHolder)holder).tvAnswer.setText(question.getAnswer());

    }

    @Override
    public int getItemCount() {
        return mTrimmedFormAnswers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvQuestion;
        private TextView tvAnswer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
