package com.example.medisoft.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST(APIClient.APPEND_URL + "login")
    Call<JsonObject> login(@Body JsonObject object);

    /*INNER PHASE ADMIN/REFFERAL*/
    @POST(APIClient.APPEND_URL + "register")
    Call<JsonObject> register(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAllSupplierbyClient")
    Call<JsonObject> getAllSupplierbyClient(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getPurchaseOrderList")
    Call<JsonObject> getPurchaseOrderList(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getShopbyId")
    Call<JsonObject> getShopbyId(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "verifyMobile")
    Call<JsonObject> verifyMobile(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAllStockByClient")
    Call<JsonObject> getAllStockByClient(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getPurchaseOrderByClient")
    Call<JsonObject> getPurchaseOrderByClient(@Body JsonObject object);







}
