package com.example.autovista.models.car;

import android.os.Parcel;
import android.os.Parcelable;

public class CarModel implements Parcelable {
    private String modelName;
    private String brand;
    private String exteriorColor;
    private String fuelType;
    private String interiorColor;
    private int mileage;
    private int price;
    private String transmission;
    private String vin;
    private int weight;

    public CarModel() {}

    public CarModel(String modelName, String brand, String exteriorColor, String fuelType,
                    String interiorColor, int mileage, int price, String transmission,
                    String vin, int weight) {
        this.modelName = modelName;
        this.brand = brand;
        this.exteriorColor = exteriorColor;
        this.fuelType = fuelType;
        this.interiorColor = interiorColor;
        this.mileage = mileage;
        this.price = price;
        this.transmission = transmission;
        this.vin = vin;
        this.weight = weight;
    }

    protected CarModel(Parcel in) {
        modelName = in.readString();
        brand = in.readString();
        exteriorColor = in.readString();
        fuelType = in.readString();
        interiorColor = in.readString();
        mileage = in.readInt();
        price = in.readInt();
        transmission = in.readString();
        vin = in.readString();
        weight = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(modelName);
        dest.writeString(brand);
        dest.writeString(exteriorColor);
        dest.writeString(fuelType);
        dest.writeString(interiorColor);
        dest.writeInt(mileage);
        dest.writeInt(price);
        dest.writeString(transmission);
        dest.writeString(vin);
        dest.writeInt(weight);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CarModel> CREATOR = new Creator<CarModel>() {
        @Override
        public CarModel createFromParcel(Parcel in) {
            return new CarModel(in);
        }

        @Override
        public CarModel[] newArray(int size) {
            return new CarModel[size];
        }
    };

    /**
     * Constructs the storage path for the car's image.
     *
     * @return The constructed storage path.
     */
    public String getStoragePath() {
        return capitalizeFirstLetter(brand) + "/" + capitalizeFirstLetter(brand) + "-" + modelName + ".png";
    }

    /**
     * Capitalizes the first letter of the given string.
     *
     * @param str The string to capitalize.
     * @return The capitalized string.
     */
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    // Getters and Setters

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getExteriorColor() {
        return exteriorColor;
    }

    public void setExteriorColor(String exteriorColor) {
        this.exteriorColor = exteriorColor;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getInteriorColor() {
        return interiorColor;
    }

    public void setInteriorColor(String interiorColor) {
        this.interiorColor = interiorColor;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
