package com.example.medisoft.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PurchaseOrder {

    @SerializedName("Item Description")
    @Expose
    private String itemDescription;
    @SerializedName("Company Name")
    @Expose
    private String companyName;
    @SerializedName("Sales Quantity")
    @Expose
    private String salesQuantity;
    @SerializedName("Quantity")
    @Expose
    private String quantity;
    @SerializedName("Pack")
    @Expose
    private String pack;
    @SerializedName("Unit Rate")
    @Expose
    private String unitRate;
    @SerializedName("Stock")
    @Expose
    private String stock;
    @SerializedName("Supplier Name")
    @Expose
    private String supplierName;

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(String salesQuantity) {
        this.salesQuantity = salesQuantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(String unitRate) {
        this.unitRate = unitRate;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

}