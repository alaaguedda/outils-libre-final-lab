package com.lab.pricing;

public class OrderSummary {

    private final double subtotal;
    private final double discountAmount;
    private final double tax;
    private final double finalPrice;

    public OrderSummary(double subtotal, double discountAmount, double tax, double finalPrice) {
        this.subtotal       = subtotal;
        this.discountAmount = discountAmount;
        this.tax            = tax;
        this.finalPrice     = finalPrice;
    }

    public double getSubtotal()       { return subtotal; }
    public double getDiscountAmount() { return discountAmount; }
    public double getTax()            { return tax; }
    public double getFinalPrice()     { return finalPrice; }

    @Override
    public String toString() {
        return String.format(
            "OrderSummary{subtotal=%.2f, discount=%.2f, tax=%.2f, final=%.2f}",
            subtotal, discountAmount, tax, finalPrice
        );
    }
}