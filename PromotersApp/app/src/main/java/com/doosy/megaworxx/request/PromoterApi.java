package com.doosy.megaworxx.request;

import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.entity.Sales;
import com.doosy.megaworxx.entity.Stock;
import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.model.AddStockModel;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.ClientToken;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.model.ServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PromoterApi {

    @Headers("Content-Type: application/json")
    @POST("Authentication/sign-in")
    Call<DataServerResponse<Promoter>> login(@Body LoginModel loginModel );


    @GET("Campaign/get-today-campaign-date")
    Call<DataServerResponse<String>> fetchTodayDate(@Header("Authorization") String token);

    @GET("Campaign/get-promoter-campaigns/{promoterid}/{campaigndate}")
    Call<DataServerResponse<CampaignModel>> fetchTodayCampaign(@Header("Authorization") String token,  @Path("promoterid") String promoterId,
                                                               @Path("campaigndate") String campaigndate);
    @Headers("Content-Type: application/json")
    @POST("Campaign/check-in-or-out")
    Call<ServerResponse> checkInOut(@Header("Authorization") String token, @Body CheckModel checkModel);

    @Headers("Content-Type: application/json")
    @POST("Campaign/save-campaign-stock")
    Call<ServerResponse> saveCampaignStock(@Header("Authorization") String token, @Body List<AddStockModel> addStockModel);

    @GET("Campaign/get-campaign-stocks/{campaignid}")
    Call<DataServerResponse<Stock>> fetchCampaignStock(@Header("Authorization") String token, @Path("campaignid") String campaignId);

    @GET("StockItems/get-all")
    Call<DataServerResponse<StockItem>> fetchItems(@Header("Authorization") String token );

    @GET("Campaign/get-promoter-stock/{promoterid}/{campaignId}/{campaignlocationid}")
    Call<DataServerResponse<Stock>> getPromoterStocks(
            @Header("Authorization") String token, @Path("promoterid") String promoterId,
                                                     @Path("campaignId") String campaignId,
                                                     @Path("campaignlocationid") String campaignlocationId);

    @GET("Campaign/get-promoter-sales/{promoterid}/{campaignId}/{campaignlocationid}")
    Call<DataServerResponse<Sales>> getPromoterSales(@Header("Authorization") String token, @Path("promoterid") String promoterId,
                                                     @Path("campaignId") String campaignId,
                                                     @Path("campaignlocationid") String campaignlocationId);

    @GET("Campaign/get-by-id")
    Call<DataServerResponse<Campaign>> getCampaignById(@Header("Authorization") String token, @Query("id") String campaignId);

    @GET("Campaign/get-promoter-sales/{promoterid}/{campaignId}/{campaignlocationid}")
    Call<DataServerResponse<Sales>> fetchPromoterSales(@Header("Authorization") String token, @Path("promoterid") String promoterid ,
                                                       @Path("campaignId") String campaignId ,
                                                       @Path("campaignlocationid") String campaignlocationid );

    @FormUrlEncoded
    @POST("connect/token")
    Call<ClientToken> getToken(@Field("grant_type") String type, @Field("scope") String scope);


    /*
    @Multipart
    @POST("/promoter/login")
    Call<DataServerResponse<Promoter>> login(@Part("loginModel") LoginModel loginModel);*/
}
