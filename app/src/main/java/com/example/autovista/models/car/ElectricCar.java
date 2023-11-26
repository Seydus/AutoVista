package com.example.autovista.models.car;

public class ElectricCar extends Car{
    //specific attributes
    public int batteryCapacity; //kilowatts per hour
    public int estRange; //distance travelled before battery is exhausted
    public int chargingSpeed;//minutes or hours depends
    public int MPGe; //Miles per Gallon equivalent

    // Constructor
    public ElectricCar(CarInfo info, int batteryCapacity, int estRange, int chargingSpeed, int MPGe) {
        super(info);
        this.batteryCapacity = batteryCapacity;
        this.estRange = estRange;
        this.chargingSpeed = chargingSpeed;
        this.MPGe = MPGe;
    }

    //Getters & Setters
    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public int getEstRange() {
        return estRange;
    }

    public void setEstRange(int estRange) {
        this.estRange = estRange;
    }

    public int getChargingSpeed() {
        return chargingSpeed;
    }

    public void setChargingSpeed(int chargingSpeed) {
        this.chargingSpeed = chargingSpeed;
    }

    public int getMPGe() {
        return MPGe;
    }

    public void setMPGe(int MPGe) {
        this.MPGe = MPGe;
    }

}