package com.example.autovista.models.Car;

import Entity.Entity;

public abstract class Car implements Entity {
    CarInfo info;

    //constructor
    public Car(CarInfo info) {
        this.info = info;
    }

    public CarInfo getInfo() {
        return info;
    }

    public void setInfo(CarInfo info) {
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

