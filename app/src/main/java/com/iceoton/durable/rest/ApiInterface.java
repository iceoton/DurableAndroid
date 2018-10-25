package com.iceoton.durable.rest;

import com.iceoton.durable.model.AssetDetailResponse;
import com.iceoton.durable.model.AssetResponse;
import com.iceoton.durable.model.ListLocationResponse;
import com.iceoton.durable.model.ListStatusResponse;
import com.iceoton.durable.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api-v1.php")
    Call<UserResponse> userLogin(@Field("tag") String tag, @Field("data") String data);

    @FormUrlEncoded
    @POST("api-v1.php")
    Call<AssetResponse> postGetAssetList(@Field("tag") String tag);

    @FormUrlEncoded
    @POST("api-v1.php")
    Call<AssetResponse> postGetAssetByType(@Field("tag") String tag, @Field("data") String data);

    @FormUrlEncoded
    @POST("api-v1.php")
    Call<AssetDetailResponse> postGetAssetDetail(@Field("tag") String tag, @Field("data") String data);

    @FormUrlEncoded
    @POST("api-v1.php")
    Call<AssetDetailResponse> postEditAsset(@Field("tag") String tag, @Field("data") String data);

    @FormUrlEncoded
    @POST("api-v1.php")
    Call<ListLocationResponse> postGetAllAssetLocation(@Field("tag") String tag);

    @FormUrlEncoded
    @POST("api-v1.php")
    Call<ListStatusResponse> postGetAllAssetStatus(@Field("tag") String tag);

}
