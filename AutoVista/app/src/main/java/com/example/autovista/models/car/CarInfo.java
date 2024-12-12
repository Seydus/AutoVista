package com.example.autovista.models.car;

public class CarInfo extends Car{
    public CarInfo(CarInfo info) {
        super(info);
    }

    public enum STATUS {
        BRAND_NEW, USED, BRAND_NEW_CERTIFIED, USED_CERTIFIED, BRAND_NEW_AND_USED_CERTIFIED
    }

    public enum FUELTYPE {
        Gas, Electric, Hybrid
    }

    public STATUS status; //used, brand new ... certified = thoroughly inspected and repaired as needed || reconditioned by manufacturer i.e. pre-owned but really good still

    public int id;
    public String make; //brands like: Toyota, Tesla
    public String model; //e.g. Camry, Galant
    public double price; //price listed for sale
    public int weight; //weight in kg(?)
    public String color;
    public FUELTYPE fuelType; // gas | electric | hybrid
    public String transmissionType; // automatic | manual | semi-automatic
    public int mileage; //amount of miles the car currently has


    //getters and setters
    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public FUELTYPE getFuelType() {
        return fuelType;
    }

    public void setFuelType(FUELTYPE fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
}
