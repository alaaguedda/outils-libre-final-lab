package com.lab.pricing;

import java.util.List;

public class PricingEngine {

    private final DiscountService discountService;
    private final TaxService taxService;

    public PricingEngine() {
        this.discountService = new DiscountService();
        this.taxService      = new TaxService();
    }

    public PricingEngine(DiscountService discountService, TaxService taxService) {
        this.discountService = discountService;
        this.taxService      = taxService;
    }

    public OrderSummary calculateOrder(List<Double> prices, List<Integer> quantities,
                                       String customerTypeStr, String discountCode) {
        CustomerType customerType = CustomerType.from(customerTypeStr);

        double subtotal       = computeSubtotal(prices, quantities);
        double discountAmount = discountService.calculateDiscount(subtotal, customerType, discountCode);
        double afterDiscount  = subtotal - discountAmount;
        double tax            = taxService.calculateTax(afterDiscount, customerType);
        double finalPrice     = afterDiscount + tax;

        return new OrderSummary(subtotal, discountAmount, tax, finalPrice);
    }

    private double computeSubtotal(List<Double> prices, List<Integer> quantities) {
        double subtotal = 0.0;
        for (int i = 0; i < prices.size(); i++) {
            subtotal += prices.get(i) * quantities.get(i);
        }
        return subtotal;
    }
}