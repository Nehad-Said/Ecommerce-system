package com.ecommerce.model.product;

import com.ecommerce.interfaces.Shippable;

public class TV extends NonExpirableProduct implements Shippable {
    private double weight;
    
    public TV(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }
    
    @Override
    public double getWeight() {
        return weight;
    }
    
    @Override
    public String toString() {
        return super.toString() + " (Weight: " + weight + "g)";
    }
}

