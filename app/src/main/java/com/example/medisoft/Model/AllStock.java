package com.example.medisoft.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllStock {

    @SerializedName("Product_Name")
    @Expose
    private String productName;
    @SerializedName("MRP")
    @Expose
    private String mrp;
    @SerializedName("Stock_id")
    @Expose
    private String stockId;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

}