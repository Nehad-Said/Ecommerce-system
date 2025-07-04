package com.ecommerce.model.product;

public abstract class Product {
    protected String name;
    protected double price;
    protected int quantity;
    
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public boolean isAvailable(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }
    
    public void reduceQuantity(int amount) {
        if (amount <= this.quantity) {
            this.quantity -= amount;
        }
    }
    
    public abstract boolean isExpired();
    
    @Override
    public String toString() {
        return name + " - $" + price + " (Stock: " + quantity + ")";
    }
}
