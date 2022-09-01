package com.example.medisoft.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultData {


    @SerializedName("User_details")
    @Expose
    private UserDetails userDetails;


    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }


    @SerializedName("SupplierbyClient")
    @Expose
    private List<SupplierByClient> suppliern = null;

    public List<SupplierByClient> getSuppliern() {
        return suppliern;
    }

    public void setSuppliern(List<SupplierByClient> suppliern) {
        this.suppliern = suppliern;
    }


    @SerializedName("Purchase Order")
    @Expose
    private List<PurchaseOrder> purchaseOrder = null;

    public List<PurchaseOrder> getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(List<PurchaseOrder> purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }


    @SerializedName("Client_details")
    @Expose
    private ClientDetails clientDetails;

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
    }

    @SerializedName("All_Stock")
    @Expose
    private List<AllStock> allStock = null;

    public List<AllStock> getAllStock() {
        return allStock;
    }

    public void setAllStock(List<AllStock> allStock) {
        this.allStock = allStock;
    }

    @SerializedName("OrderByClient")
    @Expose
    private List<OrderByClient> orderByClient = null;

    public List<OrderByClient> getOrderByClient() {
        return orderByClient;
    }

    public void setOrderByClient(List<OrderByClient> orderByClient) {
        this.orderByClient = orderByClient;
    }


    @SerializedName("AllShopsbyAdmin")
    @Expose
    private List<AllShopsbyAdmin> allShopsbyAdmin = null;

    public List<AllShopsbyAdmin> getAllShopsbyAdmin() {
        return allShopsbyAdmin;
    }

    public void setAllShopsbyAdmin(List<AllShopsbyAdmin> allShopsbyAdmin) {
        this.allShopsbyAdmin = allShopsbyAdmin;
    }

}