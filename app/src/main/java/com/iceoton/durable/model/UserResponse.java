package com.iceoton.durable.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model จำลองผลลัพธ์เวลาติดต่อไปที่ API เพื่อขอข้อมูลของ User
 */
public class UserResponse {
    @SerializedName("success")
    private int successValue;
    @SerializedName("error")
    private int errorValue;
    @SerializedName("error_msg")
    private String errorMessage;
    @SerializedName("result")
    private User result;

    public int getSuccessValue() {
        return successValue;
    }

    public int getErrorValue() {
        return errorValue;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public User getResult() {
        return result;
    }
}
