package com.example.medisoft.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderByClient {

    @SerializedName("OrderNo")
    @Expose
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}