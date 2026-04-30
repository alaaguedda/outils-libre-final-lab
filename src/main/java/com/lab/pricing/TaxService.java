package com.lab.pricing;

public class TaxService {

    private static final double REGULAR_TAX_RATE = 0.10;
    private static final double VIP_TAX_RATE     = 0.08;

    public double calculateTax(double amountAfterDiscount, CustomerType customerType) {
        double rate = (customerType == CustomerType.VIP) ? VIP_TAX_RATE : REGULAR_TAX_RATE;
        return amountAfterDiscount * rate;
    }
}