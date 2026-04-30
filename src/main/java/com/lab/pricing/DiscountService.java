package com.lab.pricing;

public class DiscountService {

    private static final double VIP_DISCOUNT_RATE    = 0.15;
    private static final double SAVE5_RATE           = 0.05;
    private static final double SAVE10_RATE          = 0.10;
    private static final double SAVE20_RATE          = 0.20;

    public double calculateDiscount(double subtotal, CustomerType customerType, String discountCode) {
        double discount = 0.0;
        discount += codeDiscount(subtotal, discountCode);
        discount += customerDiscount(subtotal, customerType);
        return discount;
    }

    private double codeDiscount(double subtotal, String code) {
        if (code == null) return 0.0;
        return switch (code.toUpperCase()) {
            case "SAVE5"  -> subtotal * SAVE5_RATE;
            case "SAVE10" -> subtotal * SAVE10_RATE;
            case "SAVE20" -> subtotal * SAVE20_RATE;
            default       -> 0.0;
        };
    }

    private double customerDiscount(double subtotal, CustomerType customerType) {
        if (customerType == CustomerType.VIP) {
            return subtotal * VIP_DISCOUNT_RATE;
        }
        return 0.0;
    }
}