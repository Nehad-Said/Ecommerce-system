package com.ecommerce;

import com.ecommerce.model.*;
import com.ecommerce.model.product.*;
import com.ecommerce.service.CheckoutService;
import com.ecommerce.exception.*;
import java.time.LocalDate;

public class ECommerceDemo {
    public static void main(String[] args) {
        // Create products
        Cheese cheese = new Cheese("Cheese", 100.0, 10, LocalDate.now().plusDays(7), 200.0);
        Biscuits biscuits = new Biscuits("Biscuits", 150.0, 5, LocalDate.now().plusDays(14), 350.0);
        TV tv = new TV("TV", 500.0, 3, 5000.0);
        MobileScratchCard scratchCard = new MobileScratchCard("Mobile Scratch Card", 25.0, 100);
        
        // Create customer
        Customer customer = new Customer("John Doe", 1000.0);
        
        // Create cart
        Cart cart = new Cart();
        
        // Create checkout service
        CheckoutService checkoutService = new CheckoutService();
        
        System.out.println("=== E-Commerce System Demo ===");
        System.out.println("Customer: " + customer);
        System.out.println();
        
        // Demo 1: Successful checkout
        System.out.println("--- Demo 1: Successful Checkout ---");
        try {
            cart.addItem(cheese, 2);
            cart.addItem(biscuits, 1);
            cart.addItem(scratchCard, 1);
            
            System.out.println("Cart items:");
            for (CartItem item : cart.getItems()) {
                System.out.println("- " + item);
            }
            System.out.println();
            
            checkoutService.processCheckout(customer, cart);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Demo 2: Out of stock error
        System.out.println("--- Demo 2: Out of Stock Error ---");
        try {
            cart.addItem(tv, 5); // Only 3 TVs available
            checkoutService.processCheckout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            cart.clear();
        }
        System.out.println();
        
        // Demo 3: Expired product error
        System.out.println("--- Demo 3: Expired Product Error ---");
        Cheese expiredCheese = new Cheese("Expired Cheese", 50.0, 5, LocalDate.now().minusDays(1), 200.0);
        try {
            cart.addItem(expiredCheese, 1);
            checkoutService.processCheckout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            cart.clear();
        }
        System.out.println();
        
        // Demo 4: Insufficient balance error
        System.out.println("--- Demo 4: Insufficient Balance Error ---");
        Customer poorCustomer = new Customer("Poor Customer", 50.0);
        try {
            cart.addItem(tv, 1);
            checkoutService.processCheckout(poorCustomer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            cart.clear();
        }
        System.out.println();
        
        // Demo 5: Empty cart error
        System.out.println("--- Demo 5: Empty Cart Error ---");
        try {
            checkoutService.processCheckout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();
        
        // Demo 6: Digital products (no shipping)
        System.out.println("--- Demo 6: Digital Products (No Shipping) ---");
        try {
            cart.addItem(scratchCard, 3);
            checkoutService.processCheckout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("=== Demo Complete ===");
        System.out.println("Final customer balance: $" + customer.getBalance());
    }
}
