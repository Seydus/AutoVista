package com.example.autovista.models;

public class CatalogItem {
    private int imageResId;
    private String modelName;
    private String brand;

    public CatalogItem(int imageResId, String modelName, String brand) {
        this.imageResId = imageResId;
        this.modelName = modelName;
        this.brand = brand;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getModelName() {
        return modelName;
    }

    public String getBrand() {
        return brand;
    }
}
