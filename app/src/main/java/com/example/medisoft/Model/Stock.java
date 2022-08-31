package com.example.medisoft.Model;

public class Stock {

    String medName,medPrice,medStock;

    public Stock(String medName, String medPrice, String medStock) {
        this.medName = medName;
        this.medPrice = medPrice;
        this.medStock = medStock;
    }

    public Stock() {
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedPrice() {
        return medPrice;
    }

    public void setMedPrice(String medPrice) {
        this.medPrice = medPrice;
    }

    public String getMedStock() {
        return medStock;
    }

    public void setMedStock(String medStock) {
        this.medStock = medStock;
    }
}
