package com.example.autovista.models.car;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class CarItemsProfile {
    List<DocumentSnapshot> modelItem;
    String brand;

    public List<DocumentSnapshot> getModelItem() {
        return modelItem;
    }

    public void setModelItem(List<DocumentSnapshot> modelItem) {
        this.modelItem = modelItem;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
