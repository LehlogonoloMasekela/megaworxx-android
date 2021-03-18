package com.doosy.megaworxx.request;

import com.doosy.megaworxx.entity.Campaign;
import com.doosy.megaworxx.entity.CampaignModel;
import com.doosy.megaworxx.entity.Feedback;
import com.doosy.megaworxx.entity.Form;
import com.doosy.megaworxx.entity.Message;
import com.doosy.megaworxx.entity.Promoter;
import com.doosy.megaworxx.entity.QuestionnaireResponse;
import com.doosy.megaworxx.entity.StockItem;
import com.doosy.megaworxx.entity.StockSaleBase;
import com.doosy.megaworxx.entity.Survey;
import com.doosy.megaworxx.model.AddStockModel;
import com.doosy.megaworxx.model.CampaignForm;
import com.doosy.megaworxx.model.CheckModel;
import com.doosy.megaworxx.model.ClientToken;
import com.doosy.megaworxx.model.DataServerResponse;
import com.doosy.megaworxx.model.LoginModel;
import com.doosy.megaworxx.model.SaleModel;
import com.doosy.megaworxx.model.ServerResponse;
import com.doosy.megaworxx.model.StatusModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PromoterApi {

    @Headers("Content-Type: application/json")
    @POST("Authentication/sign-in")
    Call<DataServerResponse<Promoter>> login(@Body LoginModel loginModel );


    @GET("Campaign/get-today-campaign-date")
    Call<DataServerResponse<String>> fetchTodayDate(@Header("Authorization") String token);


    @GET("Form/get-all-feedbacks")
    Call<DataServerResponse<Form>> fetchFeedbackForm(@Header("Authorization") String token);

    @GET("Form/get-all-surveys")
    Call<DataServerResponse<Form>> fetchSurveyForm(@Header("Authorization") String token);

    @GET("Campaign/get-promoter-campaigns/{promoterid}/{campaigndate}")
    Call<DataServerResponse<CampaignModel>> fetchTodayCampaign(@Header("Authorization") String token,
                                                               @Path("promoterid") String promoterId,
                                                               @Path("campaigndate") String campaigndate);
    @Headers("Content-Type: application/json")
    @POST("Campaign/check-in-or-out")
    Call<ServerResponse> checkInOut(@Header("Authorization") String token,
                                    @Body CheckModel checkModel);

    @Headers("Content-Type: application/json")
    @POST("Campaign/save-campaign-stock")
    Call<ServerResponse> saveCampaignStock(@Header("Authorization") String token,
                                           @Body List<AddStockModel> addStockModel);

    @Headers("Content-Type: application/json")
    @POST("Campaign/save-campaign-sale")
    Call<ServerResponse> saveCampaignSale(@Header("Authorization") String token,
                                          @Body SaleModel saleModel);

    @GET("Campaign/get-campaign-stocks/{campaignid}")
    Call<DataServerResponse<StockSaleBase>> fetchCampaignStock(@Header("Authorization") String token,
                                                       @Path("campaignid") String campaignId);

    @GET("StockItems/get-all")
    Call<DataServerResponse<StockItem>> fetchItems(@Header("Authorization") String token );

    @GET("Campaign/get-promoter-stock/{promoterid}/{campaignId}/{campaignlocationid}")
    Call<DataServerResponse<StockSaleBase>> getPromoterStocks(
            @Header("Authorization") String token, @Path("promoterid") String promoterId,
                                                     @Path("campaignId") String campaignId,
                                                     @Path("campaignlocationid") String campaignlocationId);

    @GET("Campaign/get-promoter-sales/{promoterid}/{campaignId}/{campaignlocationid}")
    Call<DataServerResponse<StockSaleBase>> getPromoterSales(@Header("Authorization") String token,
                                                     @Path("promoterid") String promoterId,
                                                     @Path("campaignId") String campaignId,
                                                     @Path("campaignlocationid") String campaignlocationId);

    @GET("Campaign/get-by-id")
    Call<DataServerResponse<Campaign>> getCampaignById(@Header("Authorization") String token,
                                                       @Query("id") String campaignId);

    @GET("Campaign/get-promoter-sales/{promoterid}/{campaignId}/{campaignlocationid}")
    Call<DataServerResponse<StockSaleBase>> fetchPromoterSales(@Header("Authorization") String token,
                                                       @Path("promoterid") String promoterid ,
                                                       @Path("campaignId") String campaignId ,
                                                       @Path("campaignlocationid") String campaignlocationid );

    @GET("Campaign/get-promoter-feedback/{promoterid}/{campaignId}/{campaignlocationid}")
    Call<DataServerResponse<Feedback>> fetchFeedBacks(@Header("Authorization") String token,
                                                   @Path("promoterid") String promoterid ,
                                                   @Path("campaignId") String campaignId ,
                                                   @Path("campaignlocationid") String campaignlocationid );

    @GET("Campaign/get-check-in/{promoterid}/{fromDate}/{toDate}")
    Call<DataServerResponse<CampaignModel>> fetchFilteredCampaign(@Header("Authorization") String token,
                                                   @Path("promoterid") String promoterid ,
                                                   @Path("fromDate") String fromDate ,
                                                   @Path("toDate") String toDate );

    @GET("Campaign/get-promoter-surveys/{promoterid}/{campaignId}/{campaignlocationid}")
    Call<DataServerResponse<Survey>> fetchSurveys(@Header("Authorization") String token,
                                                  @Path("promoterid") String promoterid ,
                                                  @Path("campaignId") String campaignId ,
                                                  @Path("campaignlocationid") String campaignlocationid );

    @GET("Campaign/get-form-respose-by-id/{id}")
    Call<DataServerResponse<QuestionnaireResponse>> fetchQuestionnaireDetail(@Header("Authorization") String token,
                                                                             @Path("id") String formId  );

    @GET("User/get-by-id")
    Call<DataServerResponse<Promoter>> getProfileById(@Header("Authorization") String token,
                                                                     @Query("id") String id  );

    @GET("Messages/get-user-messages/{userId}")
    Call<DataServerResponse<Message>> fetchMessages(@Header("Authorization") String token,
                                                    @Path("userId") String id);

    @GET("Campaign/get-check-in/{promoterId}/{campaignId}/{campaignLocationId}/{campaignDateId}")
    Call<DataServerResponse<StatusModel>> getStatus(@Header("Authorization") String token,
                                                    @Path("promoterId") String promoterId,
                                                    @Path("campaignId") String campaignId,
                                                    @Path("campaignLocationId") String campaignLocationId,
                                                    @Path("campaignDateId") String campaignDateId);

    @GET("Campaign/get-stock/{id}")
    Call<DataServerResponse<StockSaleBase>> fetchStock(@Header("Authorization") String token,
                                                       @Path("id") String id);

    @GET("Campaign/get-sale/{id}")
    Call<DataServerResponse<StockSaleBase>> fetchSale(@Header("Authorization") String token,
                                                    @Path("id") String id);

    @GET("Messages/get-by-id/{messageId}")
    Call<DataServerResponse<Message>> getMessageById(@Header("Authorization") String token,
                                                    @Path("messageId") String messageId);

    @FormUrlEncoded
    @POST("connect/token")
    Call<ClientToken> getToken(@Field("grant_type") String type, @Field("scope") String scope);

    @Headers("Content-Type: application/json")
    @POST("Campaign/save-campaign-form")
    Call<ServerResponse> saveCampaignForm(@Header("Authorization") String token,  @Body CampaignForm campaignForm);

}
