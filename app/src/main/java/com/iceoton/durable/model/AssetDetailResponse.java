package com.iceoton.durable.model;


import com.google.gson.annotations.SerializedName;

public class AssetDetailResponse extends BaseAPIResponse {

    @SerializedName("result")
    private AssetDetail result;

    public AssetDetail getResult() {
        return result;
    }
}
