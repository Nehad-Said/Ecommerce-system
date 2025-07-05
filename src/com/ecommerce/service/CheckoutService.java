package com.ecommerce.service;

import com.ecommerce.model.*;
import com.ecommerce.interfaces.Shippable;
import com.ecommerce.exception.*;
import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    private ShippingService shippingService;
    
    public CheckoutService() {
        this.shippingService = new ShippingService();
    }
    
    public void processCheckout(Customer customer, Cart cart) throws EmptyCartException, 
            OutOfStockException, ExpiredProductException, InsufficientBalanceException {
        
        // Validate cart is not empty
        if (cart.isEmpty()) {
            throw new EmptyCartException("Cart is empty. Please add items before checkout.");
        }
        
        // Validate all items in cart
        validateCartItems(cart);
        
        // Calculate totals
        double subtotal = cart.getSubtotal();
        List<Shippable> shippableItems = getShippableItems(cart);
        double shippingFee = shippingService.calculateShippingFee(shippableItems);
        double totalAmount = subtotal + shippingFee;
        
        // Validate customer balance
        if (customer.getBalance() < totalAmount) {
            throw new InsufficientBalanceException("Insufficient balance. Available: $" + 
                customer.getBalance() + ", Required: $" + totalAmount);
        }
        
        // Process payment
        customer.deductBalance(totalAmount);
        
        // Update product quantities
        for (CartItem item : cart.getItems()) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }
        
        // Process shipment
        shippingService.processShipment(shippableItems);
        
        // Print receipt
        printReceipt(cart, subtotal, shippingFee, totalAmount, customer.getBalance());
        
        // Clear cart
        cart.clear();
    }
    
    private void validateCartItems(Cart cart) throws OutOfStockException, ExpiredProductException {
        for (CartItem item : cart.getItems()) {
            // Check if product is expired
            if (item.getProduct().isExpired()) {
                throw new ExpiredProductException("Product " + item.getProduct().getName() + 
                    " has expired and cannot be purchased.");
            }
            
            // Check if product is still available
            if (!item.getProduct().isAvailable(item.getQuantity())) {
                throw new OutOfStockException("Product " + item.getProduct().getName() + 
                    " is out of stock. Available: " + item.getProduct().getQuantity() + 
                    ", Requested: " + item.getQuantity());
            }
        }
    }
    
    private List<Shippable> getShippableItems(Cart cart) {
        List<Shippable> shippableItems = new ArrayList<>();
        
        for (CartItem item : cart.getItems()) {
            if (item.getProduct() instanceof Shippable) {
                // Add each individual item (not just unique products)
                for (int i = 0; i < item.getQuantity(); i++) {
                    shippableItems.add((Shippable) item.getProduct());
                }
            }
        }
        
        return shippableItems;
    }
    
    private void printReceipt(Cart cart, double subtotal, double shippingFee, 
                             double totalAmount, double remainingBalance) {
        System.out.println("** Checkout receipt **");
        
        for (CartItem item : cart.getItems()) {
            System.out.println(item.getQuantity() + "x " + item.getProduct().getName() + 
                " " + (int)item.getTotalPrice());
        }
        
        System.out.println("----------------------");
        System.out.println("Subtotal " + (int)subtotal);
        System.out.println("Shipping " + (int)shippingFee);
        System.out.println("Amount " + (int)totalAmount);
        System.out.println("Customer balance after payment: $" + remainingBalance);
        System.out.println();
    }
}
