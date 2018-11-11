package com.iceoton.durable.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model จำลองรายละเอียดครุภัณฑ์
 */
public class AssetDetail {
    @SerializedName("id")
    private int id;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("detail")
    private String detail;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("category")
    private Category category;
    @SerializedName("location")
    private Location location;
    @SerializedName("source")
    private Source source;
    @SerializedName("status")
    private Status status;
    @SerializedName("unit")
    private Unit unit;
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

    public int getQuantity() {
        return quantity;
    }

    public Category getCategory() {
        return category;
    }

    public Location getLocation() {
        return location;
    }

    public Source getSource() {
        return source;
    }

    public Status getStatus() {
        return status;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getComeDate() {
        return comeDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public static class Category {
        private int id;
        private String code;
        private String name;

        public int getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return code.concat(" - ").concat(name);
        }
    }

    public static class Location {
        private int id;
        private String code;
        private String name;

        public int getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return code.concat(" - ").concat(name);
        }
    }

    public static class Status {
        private int id;
        private String code;
        private String name;

        public int getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return code.concat(" - ").concat(name);
        }
    }

    public static class Source {
        private int id;
        private String code;
        private String name;

        public int getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return code.concat(" - ").concat(name);
        }
    }

    public static class Unit {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
