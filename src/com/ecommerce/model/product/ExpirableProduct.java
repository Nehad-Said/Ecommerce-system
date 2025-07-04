package com.ecommerce.model.product;

import java.time.LocalDate;

public abstract class ExpirableProduct extends Product {
    protected LocalDate expiryDate;
    
    public ExpirableProduct(String name, double price, int quantity, LocalDate expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
    }
    
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }
    
    @Override
    public String toString() {
        return super.toString() + " (Expires: " + expiryDate + ")";
    }
}
