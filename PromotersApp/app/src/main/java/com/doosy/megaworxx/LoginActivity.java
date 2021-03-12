package com.doosy.megaworxx;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.util.validations.Validate;
import com.doosy.megaworxx.viewmodel.PromoterViewModel;
import com.doosy.megaworxx.viewmodel.StockViewModel;

public class LoginActivity extends BaseActivity {

    private Button btnSignIn;
    private TextView textCreateAccount;

    private EditText editEmail;
    private EditText editPassword;

    private LoginModel mLoginModel;
    private PromoterViewModel promoterViewModel;
    private LiveData<DataServerResponse<Promoter>> mPromoterResponse;
    private boolean isSubmitAttempted = false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        promoterViewModel = new ViewModelProvider(this).get(PromoterViewModel.class);

        init();

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isSubmitAttempted) return;

              if(!Validate.isValidEmail(s.toString(),
                                editEmail, (TextView)findViewById(R.id.txtErrorEmail))){
                  Drawable drawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.inputbox_border_error);
                  editEmail.setBackground(drawable);
              }else{
                  Drawable drawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.inputbox_border);
                  editEmail.setBackground(drawable);
              }
            }
        });

        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!isSubmitAttempted) return;
              if(!Validate.isValidPassword(s.toString(),
                      editPassword, (TextView)findViewById(R.id.txtErrorPassword))){
                  Drawable drawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.inputbox_border_error);
                  editPassword.setBackground(drawable);
              }else{
                  Drawable drawable = ContextCompat.getDrawable(getBaseContext(), R.drawable.inputbox_border);
                  editPassword.setBackground(drawable);
              }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSubmitAttempted = true;
                mLoginModel = new LoginModel(editEmail.getText().toString(),
                        editPassword.getText().toString());

                boolean isEmailValid =
                        Validate.isValidEmail(mLoginModel.getUsername(),
                                editEmail, (TextView)findViewById(R.id.txtErrorEmail));
                boolean isPassWord =
                        Validate.isValidPassword(mLoginModel.getPassword(),
                                editEmail, (TextView)findViewById(R.id.txtErrorPassword));

                Drawable drawable = getDrawable(R.drawable.inputbox_border_error);

                if(!isEmailValid){
                    editEmail.setBackground(drawable);
                }

                if(!isPassWord){
                    editPassword.setBackground(drawable);
                }

                if(!(isEmailValid && isPassWord)) return;

                showLoading();
                btnSignIn.setEnabled(false);
                promoterViewModel.login(mLoginModel);
                subscribe();
            }
        });

    }

    private void showDialogMessage(String header, String body){
        BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance(header, body);
        bottomSheetFragment.show(getSupportFragmentManager(), "login");
    }

    private void subscribe() {
        mPromoterResponse = promoterViewModel.getDataResponse();

        mPromoterResponse.observe(this, new Observer<DataServerResponse<Promoter>>() {
            @Override
            public void onChanged(DataServerResponse<Promoter> response) {
                btnSignIn.setEnabled(true);
                mPromoterResponse.removeObserver(this);
                if(mPromoterResponse.hasActiveObservers()){
                    mPromoterResponse.removeObservers(LoginActivity.this);
                }

                if(response != null && response.isSuccessful()){
                    Promoter promoter = response.getDataList().get(0);
                    settings.setUserId(promoter.getUserId());
                    settings.setIsLoggedIn(true);
                    Bundle bundle = new Bundle();
                    String promoterKey = getString(R.string.key_promoter);
                    bundle.putSerializable(promoterKey, promoter);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

                hideLoading();

                if(response != null && !response.isSuccessful()){

                    showError("Username and password do not match.");
                    /*showDialogMessage("Bad credentials",
                            "Username and password do not match.");*/
                }

                if(response == null){
                    showDialogMessage("Error", "Oops something terrible" +
                            " happened, please try again later.");

                }

            }
        });
    }

    private void init() {
        btnSignIn = findViewById(R.id.signInButton);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
    }
}
