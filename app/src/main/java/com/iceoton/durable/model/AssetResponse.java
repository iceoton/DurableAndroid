package com.iceoton.durable.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AssetResponse {
    @SerializedName("success")
    private int successValue;
    @SerializedName("error")
    private int errorValue;
    @SerializedName("error_msg")
    private String errorMessage;
    @SerializedName("result")
    private ArrayList<Asset> result;

    public int getSuccessValue() {
        return successValue;
    }

    public int getErrorValue() {
        return errorValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ArrayList<Asset> getResult() {
        return result;
    }
}
