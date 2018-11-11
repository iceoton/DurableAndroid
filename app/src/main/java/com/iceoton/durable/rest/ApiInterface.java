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

/***
 * Interface สำหรับส่งให้ Retrofit Library
 * จะเป็นที่รวมฟังก์ชันที่จะติดต่อไปที่ API ซึ่งก็คือการสร้าง HTTP request ติดต่อไปที่เว็บ
 * โดยจะต้องระบุว่าจะติดต่อด้วยวิธีใด (Post, Get, Put, ...) ติดต่อด้วยข้อมูลอะไร
 *  (@field, @body, ...) และได้ผลลัพธ์กลับมายังไง (Call<รูปแบบผลลัพธ์> )
 */
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
