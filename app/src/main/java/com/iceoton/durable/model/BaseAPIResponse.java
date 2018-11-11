package com.iceoton.durable.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model จำลองผลลัพธ์พื้นฐานเวลาติดต่อไปที่ API
 */
public class BaseAPIResponse {
    @SerializedName("success")
    private int successValue;
    @SerializedName("error")
    private int errorValue;
    @SerializedName("error_msg")
    private String errorMessage;

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
