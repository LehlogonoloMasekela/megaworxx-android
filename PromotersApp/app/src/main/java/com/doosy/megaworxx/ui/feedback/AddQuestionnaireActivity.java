package com.doosy.megaworxx.ui.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.adapter.AddQuestionaireAdapter;
import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Form;
import com.doosy.megaworxx.entity.FormQuestions;
import com.doosy.megaworxx.model.CampaignForm;
import com.doosy.megaworxx.model.CampaignPromoterFormAnswers;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.QuestionnaireType;
import com.doosy.megaworxx.viewmodel.QuestionnaireViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionnaireActivity extends BaseActivity implements View.OnClickListener {

    private QuestionnaireViewModel mQuestionnaireViewModel;
    private LiveData<DataServerResponse<Form>> mFormResponse;
    private LiveData<ServerResponse> mResponse;
    private EditText etCustomer;
    private TextView tvTopHeader;
    private Button btnSave, btnCancel;
    private RecyclerView mRecyclerviewQuestions;
    private AddQuestionaireAdapter mQuestionAdapter;
    private Form mForm;
    private String mCustomerName;
    private CampaignModel mCampaignModel;
    private Campaign mCampaign;

    private QuestionnaireType mQuestionnaireType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);
        mQuestionnaireViewModel = new ViewModelProvider(this).get(QuestionnaireViewModel.class);

        initViews();

        String keyCampaign = getString(R.string.key_campaign);
        String keyCampaignModel = getString(R.string.key_campaign_model);
        String keyQuestionnaireType = getString(R.string.key_campaign_page_type);

        if(getIntent().hasExtra(keyCampaign) && getIntent().hasExtra(keyCampaignModel)) {
            mCampaign = (Campaign) getIntent().getSerializableExtra(keyCampaign);
            mCampaignModel = (CampaignModel) getIntent().getSerializableExtra(keyCampaignModel);
        }

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        showLoading();

        if(getIntent().hasExtra(keyQuestionnaireType)){
            mQuestionnaireType = (QuestionnaireType) getIntent()
                    .getSerializableExtra(keyQuestionnaireType);

            if(mQuestionnaireType == QuestionnaireType.FeedBack)
                mQuestionnaireViewModel.fetchFeedBackForm(settings.getToken());
            else if(mQuestionnaireType == QuestionnaireType.Survey)
                mQuestionnaireViewModel.fetchSurveyForm(settings.getToken());

            displayInfo();
        }

        fetchForm();
    }

    private void displayInfo(){
        String topHeader = "";
        String toolbarTitle = "";

        if(mQuestionnaireType == QuestionnaireType.FeedBack)
        {
            topHeader = getString(R.string.new_feedback_header);
            toolbarTitle = getString(R.string.add_feedback_title);
        }else if(mQuestionnaireType == QuestionnaireType.Survey){
            topHeader = getString(R.string.new_survey_header);
            toolbarTitle = getString(R.string.add_survey_title);
        }

        tvTopHeader.setText(topHeader);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarTitle);
    }

    private void initViews(){
        mRecyclerviewQuestions = findViewById(R.id.mRecyclerviewQuestions);
        etCustomer = findViewById(R.id.etCustomer);
        tvTopHeader = findViewById(R.id.topHeader);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void fetchForm() {
        mFormResponse = mQuestionnaireViewModel.getQuestionsResponse();

        mFormResponse.observe(this, new Observer<DataServerResponse<Form>>() {
            @Override
            public void onChanged(DataServerResponse<Form> formDataServerResponse) {
                hideLoading();
                if(formDataServerResponse == null){
                    //Show appropriate message here
                    Log.d(Constants.TAG, "Returned null here");
                    return;
                }

                if(formDataServerResponse.isSuccessful()){

                    if(formDataServerResponse.getDataList().size() > 0){
                        //Process data here
                        mForm = formDataServerResponse.getDataList().get(0);

                        if(mForm.getFormQuestions() != null){
                            initRecyclerView(mForm.getFormQuestions());
                            mQuestionAdapter.notifyDataSetChanged();
                        }
                    }else{
                        //No survey form is created
                        Log.d(Constants.TAG, "No data fetched here.");
                    }

                }
            }
        });
    }

    private void initRecyclerView(List<FormQuestions> formQuestions) {
        mRecyclerviewQuestions.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mQuestionAdapter = new AddQuestionaireAdapter(formQuestions, this);
        mRecyclerviewQuestions.setAdapter(mQuestionAdapter);
    }

    public void setAnswer(int position, String answer){
        mForm.getFormQuestions().get(position).setAnswer(answer);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnSave){
            saveAnswers();
        }else if(v.getId() == R.id.btnCancel){
            finish();
        }
    }

    StringBuilder error;

    private boolean isFormValid(){

        error = new StringBuilder();

        mCustomerName = etCustomer.getText().toString();
        if(mCustomerName.isEmpty())
        {
            error.append("Customer name is required");
            return false;
        }

        for(FormQuestions question: mForm.getFormQuestions()){

            if(question.getAnswer()  == null || question.getAnswer() .isEmpty()){
                String questionStr = question.getQuestion();
                StringBuilder msg = new StringBuilder();
                msg.append(questionStr);
                msg.append(" is not answered.");
                error.append(msg);
                return false;
            }

        }

        return true;
    }

    private void saveAnswers() {

        if(!isFormValid()){
            Snackbar.make(findViewById(R.id.mainLayout), error.toString(),
                    BaseTransientBottomBar.LENGTH_SHORT ).show();
            return;
        }

        List<CampaignPromoterFormAnswers> answers = new ArrayList<>();

        for(FormQuestions question : mForm.getFormQuestions()){
            answers.add(new CampaignPromoterFormAnswers(question.getId(), question.getAnswer()));
        }

        CampaignForm formAnswer = new CampaignForm(mForm.getId(), mCustomerName,
                mCampaignModel.getPromoterId(), mCampaignModel.getCampaignDateId(),
                mCampaignModel.getCampaignId(),mCampaignModel.getCampaignLocationId(),
                mCampaignModel.getLocation(), answers);

        showLoading();

        mQuestionnaireViewModel.saveQuestionnaireForm(settings.getToken(), formAnswer);

        subscribe();

    }

    private void subscribe(){
        mResponse = mQuestionnaireViewModel.getResponse();

        mResponse.observe(this, new Observer<ServerResponse>() {
            @Override
            public void onChanged(ServerResponse response) {

                Log.d(Constants.TAG, response.toString());
                hideLoading();

                if(response == null){
                    showError("Error occurred, please try again later.");
                    return;
                }

                if(!response.isSuccessful()){

                    StringBuilder strBuilderMsg = new StringBuilder();
                    String message = "Error occurred, please try again later.";
                    strBuilderMsg.append(message);

                    if(response.getMessages().size() >= 1){
                        strBuilderMsg = new StringBuilder();
                        for (String msg: response.getMessages()
                        ) {
                            strBuilderMsg.append(msg);
                            strBuilderMsg.append(System.getProperty("line.separator"));
                        }
                    }

                    showError(strBuilderMsg.toString());
                    return;
                }

                if(response.isSuccessful()){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isSuccessful", true);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });
    }
}
