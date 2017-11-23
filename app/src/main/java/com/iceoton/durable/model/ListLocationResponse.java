package com.iceoton.durable.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListLocationResponse {
    @SerializedName("success")
    private int successValue;
    @SerializedName("error")
    private int errorValue;
    @SerializedName("error_msg")
    private String errorMessage;
    @SerializedName("result")
    private ArrayList<AssetDetail.Location> result;

    public ArrayList<AssetDetail.Location> getResult() {
        return result;
    }

    public void setResult(ArrayList<AssetDetail.Location> result) {
        this.result = result;
    }

    public int getSuccessValue() {
        return successValue;
    }

    public void setSuccessValue(int successValue) {
        this.successValue = successValue;
    }

    public int getErrorValue() {
        return errorValue;
    }

    public void setErrorValue(int errorValue) {
        this.errorValue = errorValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
