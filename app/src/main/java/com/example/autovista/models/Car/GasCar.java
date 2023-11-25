package com.example.autovista.models.Car;

public class GasCar extends Car{
    //specific attributes
    public float kilometerPerLiter; // or you can use miles per gallon (MPG) which is more common in websites i found


    // Constructor
    public GasCar(CarInfo info, float kilometerPerLiter) {
        super(info);
        this.kilometerPerLiter = kilometerPerLiter;
    }

    //Getter and Setter
    public float getKilometerPerLiter() {
        return kilometerPerLiter;
    }
    public void setKilometerPerLiter(float kilometerPerLiter) {
        this.kilometerPerLiter = kilometerPerLiter;
    }

}