package com.example.medisoft.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientDetails {

    @SerializedName("ClientID")
    @Expose
    private String clientID;
    @SerializedName("ClientName")
    @Expose
    private String clientName;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Address1")
    @Expose
    private String address1;
    @SerializedName("Address2")
    @Expose
    private String address2;
    @SerializedName("Address3")
    @Expose
    private String address3;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Cflag")
    @Expose
    private String cflag;
    @SerializedName("TIN")
    @Expose
    private String tin;
    @SerializedName("DL")
    @Expose
    private String dl;
    @SerializedName("Sales_Category")
    @Expose
    private String salesCategory;
    @SerializedName("DL2")
    @Expose
    private String dl2;
    @SerializedName("BranchID")
    @Expose
    private String branchID;
    @SerializedName("Constituency")
    @Expose
    private String constituency;
    @SerializedName("Opening_Date")
    @Expose
    private String openingDate;
    @SerializedName("Inactive")
    @Expose
    private String inactive;
    @SerializedName("CreditDays")
    @Expose
    private String creditDays;
    @SerializedName("AllowOnlineOrders")
    @Expose
    private String allowOnlineOrders;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("BillingID")
    @Expose
    private String billingID;
    @SerializedName("IGSTCustomer")
    @Expose
    private String iGSTCustomer;
    @SerializedName("DefaultBank")
    @Expose
    private String defaultBank;
    @SerializedName("Note")
    @Expose
    private String note;
    @SerializedName("PinCode")
    @Expose
    private String pinCode;
    @SerializedName("CreditLimit")
    @Expose
    private String creditLimit;
    @SerializedName("IsBranch")
    @Expose
    private String isBranch;
    @SerializedName("IsCustomer")
    @Expose
    private String isCustomer;

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCflag() {
        return cflag;
    }

    public void setCflag(String cflag) {
        this.cflag = cflag;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getDl() {
        return dl;
    }

    public void setDl(String dl) {
        this.dl = dl;
    }

    public String getSalesCategory() {
        return salesCategory;
    }

    public void setSalesCategory(String salesCategory) {
        this.salesCategory = salesCategory;
    }

    public String getDl2() {
        return dl2;
    }

    public void setDl2(String dl2) {
        this.dl2 = dl2;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getInactive() {
        return inactive;
    }

    public void setInactive(String inactive) {
        this.inactive = inactive;
    }

    public String getCreditDays() {
        return creditDays;
    }

    public void setCreditDays(String creditDays) {
        this.creditDays = creditDays;
    }

    public String getAllowOnlineOrders() {
        return allowOnlineOrders;
    }

    public void setAllowOnlineOrders(String allowOnlineOrders) {
        this.allowOnlineOrders = allowOnlineOrders;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBillingID() {
        return billingID;
    }

    public void setBillingID(String billingID) {
        this.billingID = billingID;
    }

    public String getIGSTCustomer() {
        return iGSTCustomer;
    }

    public void setIGSTCustomer(String iGSTCustomer) {
        this.iGSTCustomer = iGSTCustomer;
    }

    public String getDefaultBank() {
        return defaultBank;
    }

    public void setDefaultBank(String defaultBank) {
        this.defaultBank = defaultBank;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(String creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getIsBranch() {
        return isBranch;
    }

    public void setIsBranch(String isBranch) {
        this.isBranch = isBranch;
    }

    public String getIsCustomer() {
        return isCustomer;
    }

    public void setIsCustomer(String isCustomer) {
        this.isCustomer = isCustomer;
    }
}
