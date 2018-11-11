package com.iceoton.durable.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Model จำลองผลลัพธ์เวลาติดต่อไปที่ API เพื่อขอรายการสถานะครุภัณฑ์
 */
public class ListStatusResponse {
    @SerializedName("success")
    private int successValue;
    @SerializedName("error")
    private int errorValue;
    @SerializedName("error_msg")
    private String errorMessage;
    @SerializedName("result")
    private ArrayList<AssetDetail.Status> result;

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

    public ArrayList<AssetDetail.Status> getResult() {
        return result;
    }

    public void setResult(ArrayList<AssetDetail.Status> result) {
        this.result = result;
    }
}
