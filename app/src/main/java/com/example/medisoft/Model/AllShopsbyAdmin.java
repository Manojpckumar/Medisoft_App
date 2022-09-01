package com.example.medisoft.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllShopsbyAdmin {

    @SerializedName("ClientId")
    @Expose
    private String clientId;
    @SerializedName("ClientName")
    @Expose
    private String clientName;
    @SerializedName("Type")
    @Expose
    private String type;



    public AllShopsbyAdmin(String clientId, String clientName) {
        this.clientId=clientId;
        this.clientName=clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return clientName;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AllShopsbyAdmin){
            AllShopsbyAdmin c = (AllShopsbyAdmin )obj;
            if(c.getClientName().equals(getClientName()) && c.getClientId()==getClientId() ) return true;
        }

        return false;
    }

}
