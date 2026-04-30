package com.lab.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PricingEngineTest {

    private PricingEngine engine;

    @BeforeEach
    void setUp() {
        engine = new PricingEngine();
    }

    @Test
    void testSubtotalCalculation() {
        List<Double> prices = List.of(10.0, 20.0);
        List<Integer> quantities = List.of(2, 3);
        double result = engine.getSubtotal(prices, quantities);
        assertEquals(80.0, result, 0.001);
    }

    @Test
    void testRegularCustomerNoDiscount() {
        List<Double> prices = List.of(100.0);
        List<Integer> quantities = List.of(1);
        double result = engine.calc(prices, quantities, "REGULAR", null);
        // subtotal=100, disc=0, tax=10%, total=110
        assertEquals(110.0, result, 0.001);
    }

    @Test
    void testSave10DiscountCode() {
        List<Double> prices = List.of(100.0);
        List<Integer> quantities = List.of(1);
        double result = engine.calc(prices, quantities, "REGULAR", "SAVE10");
        // subtotal=100, disc=10, afterDisc=90, tax=9, total=99
        assertEquals(99.0, result, 0.001);
    }

    @Test
    void testSave20DiscountCode() {
        List<Double> prices = List.of(100.0);
        List<Integer> quantities = List.of(1);
        double result = engine.calc(prices, quantities, "REGULAR", "SAVE20");
        // subtotal=100, disc=20, afterDisc=80, tax=8, total=88
        assertEquals(88.0, result, 0.001);
    }

    @Test
    void testVipCustomerDiscount() {
        List<Double> prices = List.of(100.0);
        List<Integer> quantities = List.of(1);
        double result = engine.calc(prices, quantities, "VIP", null);
        // subtotal=100, disc=15(VIP), afterDisc=85, tax=6.8(VIP 8%), total=91.8
        assertEquals(91.8, result, 0.001);
    }

    @Test
    void testVipWithDiscountCode() {
        List<Double> prices = List.of(100.0);
        List<Integer> quantities = List.of(1);
        double result = engine.calc(prices, quantities, "VIP", "SAVE10");
        // subtotal=100, disc=10+15=25, afterDisc=75, tax=6, total=81
        assertEquals(81.0, result, 0.001);
    }

    @Test
    void testMultipleItems() {
        List<Double> prices = List.of(10.0, 5.0, 20.0);
        List<Integer> quantities = List.of(3, 4, 1);
        double result = engine.calc(prices, quantities, "REGULAR", null);
        // subtotal = 30+20+20 = 70, tax=7, total=77
        assertEquals(77.0, result, 0.001);
    }
}