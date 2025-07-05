package com.ecommerce.model;

import com.ecommerce.model.product.Product;
import com.ecommerce.exception.OutOfStockException;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    
    public Cart() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(Product product, int quantity) throws OutOfStockException {
        if (!product.isAvailable(quantity)) {
            throw new OutOfStockException("Insufficient stock for " + product.getName() + 
                ". Available: " + product.getQuantity() + ", Requested: " + quantity);
        }
        
        // Check if product already exists in cart
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                int totalQuantity = item.getQuantity() + quantity;
                if (!product.isAvailable(totalQuantity)) {
                    throw new OutOfStockException("Insufficient stock for " + product.getName() + 
                        ". Available: " + product.getQuantity() + ", Total requested: " + totalQuantity);
                }
                items.remove(item);
                items.add(new CartItem(product, totalQuantity));
                return;
            }
        }
        
        items.add(new CartItem(product, quantity));
    }
    
    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    public void clear() {
        items.clear();
    }
    
    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }
}
