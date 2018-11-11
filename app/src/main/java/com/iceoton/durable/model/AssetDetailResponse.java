package com.iceoton.durable.model;


import com.google.gson.annotations.SerializedName;

/**
 * Model จำลองผลลัพธ์เวลาติดต่อไปที่ API เพื่อขอข้อมูลรายละเอียดครุภัณฑ์
 */
public class AssetDetailResponse extends BaseAPIResponse {

    @SerializedName("result")
    private AssetDetail result;

    public AssetDetail getResult() {
        return result;
    }
}
