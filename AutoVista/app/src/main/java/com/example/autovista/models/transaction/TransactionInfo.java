package com.example.autovista.models.transaction;


import com.example.autovista.models.Entity;

public class TransactionInfo implements Entity {
    //attributes
    public int transactionId;
    public int buyerId; //who bought
    public String transactionDescription; //small desc, optional
    public PAYMENTMETHOD paymentMethod; //card or cash
    public int id;

    public enum PAYMENTMETHOD {
        Cash, Card
    }

    //Setters and Getters
    public int getId() { //get transact id
        return transactionId;
    }
    public void setId(int transactionId) { //set transact id
        this.transactionId = transactionId;
    }
    public int getBuyerId() {
        return buyerId;
    }
    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }
    public String transactionDescription() {
        return transactionDescription;
    }
    public void transactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }
    public PAYMENTMETHOD getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PAYMENTMETHOD paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}