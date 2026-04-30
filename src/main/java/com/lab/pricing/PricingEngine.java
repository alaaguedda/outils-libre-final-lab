package com.lab.pricing;

import java.util.List;

// BAD DESIGN: everything crammed into one class, magic numbers, no separation of concerns
public class PricingEngine {

    public double calc(List<Double> prices, List<Integer> quantities,
                       String customerType, String discountCode) {

        double sub = 0;
        for (int i = 0; i < prices.size(); i++) {
            sub = sub + prices.get(i) * quantities.get(i);
        }

        double disc = 0;
        if (discountCode != null) {
            if (discountCode.equals("SAVE10")) {
                disc = sub * 0.10;
            } else if (discountCode.equals("SAVE20")) {
                disc = sub * 0.20;
            } else if (discountCode.equals("SAVE5")) {
                disc = sub * 0.05;
            }
        }

        if (customerType != null && customerType.equals("VIP")) {
            disc = disc + sub * 0.15;
        }

        double afterDisc = sub - disc;

        double tax = 0;
        if (customerType != null && customerType.equals("VIP")) {
            tax = afterDisc * 0.08;
        } else {
            tax = afterDisc * 0.10;
        }

        double total = afterDisc + tax;
        return total;
    }

    // returns subtotal only - duplicates loop logic
    public double getSubtotal(List<Double> p, List<Integer> q) {
        double s = 0;
        for (int i = 0; i < p.size(); i++) {
            s += p.get(i) * q.get(i);
        }
        return s;
    }

    // returns discount - depends on magic strings
    public double getDiscount(double subtotal, String code, String ctype) {
        double d = 0;
        if (code.equals("SAVE10")) d = subtotal * 0.10;
        if (code.equals("SAVE20")) d = subtotal * 0.20;
        if (code.equals("SAVE5"))  d = subtotal * 0.05;
        if (ctype.equals("VIP"))   d += subtotal * 0.15;
        return d;
    }
}