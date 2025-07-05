package com.ecommerce.service;

import com.ecommerce.interfaces.Shippable;
import java.util.List;

public class ShippingService {
    private static final double BASE_SHIPPING_FEE = 10.0;
    private static final double WEIGHT_RATE = 0.02; // $0.02 per gram
    
    public double calculateShippingFee(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return 0.0;
        }
        
        double totalWeight = shippableItems.stream()
            .mapToDouble(Shippable::getWeight)
            .sum();
        
        double shippingFee = BASE_SHIPPING_FEE + (totalWeight * WEIGHT_RATE);
        return Math.round(shippingFee * 100.0) / 100.0; // Round to 2 decimal places
    }
    
    public void processShipment(List<Shippable> shippableItems) {
        if (shippableItems.isEmpty()) {
            return;
        }
        
        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        
        for (Shippable item : shippableItems) {
            System.out.println("1x " + item.getName() + " " + item.getWeight() + "g");
            totalWeight += item.getWeight();
        }
        
        System.out.println("Total package weight " + (totalWeight / 1000) + "kg");
        System.out.println();
    }
}
