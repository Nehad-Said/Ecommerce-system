package com.ecommerce.model.product;

public class MobileScratchCard extends NonExpirableProduct {
    
    public MobileScratchCard(String name, double price, int quantity) {
        super(name, price, quantity);
    }
    
    @Override
    public String toString() {
        return super.toString() + " (Digital - No shipping required)";
    }
}
