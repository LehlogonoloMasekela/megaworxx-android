package com.doosy.megaworxx;

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
import androidx.lifecycle.ViewModelProviders;

import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.ui.BaseActivity;
import com.doosy.megaworxx.util.Constants;
import com.doosy.megaworxx.viewmodel.PromoterViewModel;

public class LoginActivity extends BaseActivity {

    private Button btnSignIn;
    private TextView textCreateAccount;

    private EditText editEmail;
    private EditText editPassword;

    private LoginModel mLoginModel;
    private PromoterViewModel mPromoterViewModel;
    private LiveData<DataServerResponse<Promoter>> mPromoterResponse;
    int count = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mPromoterViewModel = ViewModelProviders.of(this).get(PromoterViewModel.class);

        init();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constants.TAG, "Clicked: " + count++);
                //mLoginModel = new LoginModel(editEmail.getText().toString(), editPassword.getText().toString());

                //mPromoterViewModel.login(mLoginModel);
                //subscribe();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });

    }

    private void showDialogMessage(String header, String body){
        BottomSheetFragment bottomSheetFragment = BottomSheetFragment.newInstance(header, body);
        bottomSheetFragment.show(getSupportFragmentManager(), "login");
    }

    private void subscribe() {
        mPromoterResponse = mPromoterViewModel.getLoginResponse();

        mPromoterResponse.observe(this, new Observer<DataServerResponse<Promoter>>() {
            @Override
            public void onChanged(DataServerResponse<Promoter> response) {

                mPromoterResponse.removeObserver(this);
                if(mPromoterResponse.hasActiveObservers()){
                    mPromoterResponse.removeObservers(LoginActivity.this);
                }

                if(response == null){
                    showDialogMessage("Error", "Oops something terrible" +
                            " happened, please try again later.");
                    return;
                }

                if(response.isSuccessful()){
                    Promoter person = response.getData();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    showLoading(false);
                    String msg = "Error has occurred please try again later.";
                    if(response.getMessages().size() > 0){
                        msg = response.getMessages().get(0);
                    }
                    showDialogMessage("Error", msg);
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
