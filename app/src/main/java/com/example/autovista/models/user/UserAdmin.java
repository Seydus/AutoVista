package com.example.autovista.models.user;
import java.util.Arrays;

public class UserAdmin extends User{ //we are the sellers
    //specific attributes
    public int[] itemsUpForSale; //an array of item's ID sold by this user
    public int moneyGenerated; //amount of monetary sales in Php

    //Setters and getters
    public int[] getItemsUpForSale() {//gets a COPY of the id list
        return Arrays.copyOf(itemsUpForSale, itemsUpForSale.length);
    }
    public void addItemUpForSale(int item) { //add an item ID to the end of the list, then updates the orig array
        int[] newArray = new int[itemsUpForSale.length + 1];
        System.arraycopy(itemsUpForSale, 0, newArray, 0, itemsUpForSale.length);
        newArray[itemsUpForSale.length] = item;
        this.itemsUpForSale = newArray;
    }
    public int getMoneyGenerated() {
        return moneyGenerated;
    }
    public void setMoneyGenerated(int money) {
        this.moneyGenerated = money;
    }

}

