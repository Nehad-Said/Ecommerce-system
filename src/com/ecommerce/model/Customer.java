package com.ecommerce.model;

import com.ecommerce.exception.InsufficientBalanceException;

public class Customer {
    private String name;
    private double balance;
    
    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
    
    public String getName() {
        return name;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public void deductBalance(double amount) throws InsufficientBalanceException {
        if (balance < amount) {
            throw new InsufficientBalanceException("Insufficient balance. Available: $" + 
                balance + ", Required: $" + amount);
        }
        balance -= amount;
    }
    
    @Override
    public String toString() {
        return name + " (Balance: $" + balance + ")";
    }
}
