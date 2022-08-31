package com.example.medisoft.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupplierByClient {

    @SerializedName("Supplier_Name")
    @Expose
    private String supplierName;
    @SerializedName("AutoID")
    @Expose
    private Integer autoID;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getAutoID() {
        return autoID;
    }

    public void setAutoID(Integer autoID) {
        this.autoID = autoID;
    }
}
