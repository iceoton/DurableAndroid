package com.iceoton.durable.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model จำลองครุภัณฑ์
 */
public class Asset {
    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("detail")
    private String detail;
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("location_id")
    private int locationId;
    @SerializedName("source_id")
    private int sourceId;
    @SerializedName("status_id")
    private int statusId;
    @SerializedName("unit_id")
    private int unitId;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("come_date")
    private String comeDate;
    @SerializedName("update_date")
    private String updateDate;

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getLocationId() {
        return locationId;
    }

    public int getSourceId() {
        return sourceId;
    }

    public int getStatusId() {
        return statusId;
    }

    public int getUnitId() {
        return unitId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getComeDate() {
        return comeDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
