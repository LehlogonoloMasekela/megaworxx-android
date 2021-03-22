package com.doosy.megaworxx.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.ViewQuestionnaireAdapter;
import com.doosy.megaworxx.entity.QuestionnaireResponse;
import com.doosy.megaworxx.entity.TrimmedFormAnswer;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.util.QuestionnaireType;
import com.doosy.megaworxx.viewmodel.QuestionnaireDetailViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class ViewAnswersActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ViewQuestionnaireAdapter mViewQuestionnaireAdapter;

    private QuestionnaireDetailViewModel mQuestionnaireDetailViewModel;

    private LiveData<DataServerResponse<QuestionnaireResponse>> mDataResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answers);
        initViews();
        mQuestionnaireDetailViewModel = new ViewModelProvider(this).get(QuestionnaireDetailViewModel.class);
        showLoading();
        String keyFormId = getString(R.string.key_form_id);
        String keyQuestionnaireType = getString(R.string.key_campaign_page_type);


        if(getIntent().hasExtra(keyFormId) && getIntent().hasExtra(keyQuestionnaireType)) {

            String formId = getIntent().getExtras().getString(keyFormId);
            final QuestionnaireType questionnaireType = (QuestionnaireType) getIntent().getSerializableExtra(keyQuestionnaireType);

            mQuestionnaireDetailViewModel.fetchQuestionnaireDetail(settings.getToken(), formId);

            mDataResponse = mQuestionnaireDetailViewModel.getDataResponse();

            mDataResponse.observe(this, new Observer<DataServerResponse<QuestionnaireResponse>>() {
                @Override
                public void onChanged(DataServerResponse<QuestionnaireResponse> response) {
                   hideLoading();
                    if(response != null && response.isSuccessful()){
                        if(response.getDataList().size() > 0){
                            QuestionnaireResponse questionnaireResponse = response.getDataList().get(0);
                            MaterialToolbar toolbar = findViewById(R.id.toolbar);
                            toolbar.setTitle(questionnaireResponse.getCustomerName());
                            String header = getString(R.string.feedback_answers_header);

                            if(questionnaireType == QuestionnaireType.Survey)
                                header = getString(R.string.survey_answers_header);

                            ((TextView)findViewById(R.id.topHeader)).setText(header);
                            initRecyclerView(questionnaireResponse.getTrimmedFormAnswers());
                        }

                    }
                }
            });
        }

    }

    @Override
    public void displayPage(boolean hasContent) {

    }

    @Override
    public void retryLoad() {

    }

    private void initViews(){
        recyclerView = findViewById(R.id.recyclerViewAnswers);
    }

    private void initRecyclerView(List<TrimmedFormAnswer> trimmedFormAnswerList){
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mViewQuestionnaireAdapter = new ViewQuestionnaireAdapter(trimmedFormAnswerList,this);
        recyclerView.setAdapter(mViewQuestionnaireAdapter);
    }
}
