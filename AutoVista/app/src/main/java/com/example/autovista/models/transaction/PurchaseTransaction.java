package com.example.autovista.models.transaction;

public class PurchaseTransaction extends Transaction {
    //Specific Attributes
    public int itemPurchased; //id of items to be bought
    public String vehiclePurchased; //Name of Vehicle
    public float totalCost;

    public PurchaseTransaction(TransactionInfo info, int itemPurchased, String vehiclePurchased, float totalCost) {
        super(info);
        this.itemPurchased = itemPurchased;
        this.vehiclePurchased = vehiclePurchased;
        this.totalCost = totalCost;
    }

    //Getters and Setters
    public int getItemPurchased() {
        return itemPurchased;
    }

    public void setItemPurchased(int itemPurchased) {
        this.itemPurchased = itemPurchased;
    }

    public String getVehiclePurchased() {
        return vehiclePurchased;
    }

    public void setVehiclePurchased(String vehiclePurchased) {
        this.vehiclePurchased = vehiclePurchased;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
}
