package com.example.autovista.models.Transactions;

import Entity.Entity;

public abstract class Transactions implements Entity {
    TransactionsInfo info;

    //constructor
    public Transactions(TransactionsInfo info) {
        this.info = info;
    }

    public TransactionsInfo getInfo() {
        return info;
    }

    public void setInfo(TransactionsInfo info) {
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
