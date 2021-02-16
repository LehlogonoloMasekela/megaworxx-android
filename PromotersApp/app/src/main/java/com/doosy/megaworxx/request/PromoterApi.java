package com.doosy.megaworxx.request;

import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.model.ServerResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PromoterApi {

    @POST("promoter/login")
    Call<DataServerResponse<Promoter>> login(@Body LoginModel loginModel);

    /*
    @Multipart
    @POST("/promoter/login")
    Call<DataServerResponse<Promoter>> login(@Part("loginModel") LoginModel loginModel);*/
}
