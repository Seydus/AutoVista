package com.example.autovista.models;

public class CarouselItem {
    private int imageResId;
    private String carName;

    public CarouselItem(int imageResId, String carName) {
        this.imageResId = imageResId;
        this.carName = carName;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getCarName() {
        return carName;
    }
}
