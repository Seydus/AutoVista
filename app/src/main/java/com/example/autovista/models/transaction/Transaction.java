package com.example.autovista.models.transaction;

import Entity.Entity;

public abstract class Transaction implements Entity {
    TransactionInfo info;

    //constructor
    public Transaction(TransactionInfo info) {
        this.info = info;
    }

    public TransactionInfo getInfo() {
        return info;
    }

    public void setInfo(TransactionInfo info) {
        this.info = info;
    }

    @Override
    public int getId() {
        return info.id;
    }
    public void setId(int id) {
        this.info.id = id;
    }

}
