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
        OrderSummary summary = engine.calculateOrder(
            List.of(10.0, 20.0), List.of(2, 3), "REGULAR", null);
        assertEquals(80.0, summary.getSubtotal(), 0.001);
    }

    @Test
    void testRegularCustomerNoDiscount() {
        OrderSummary summary = engine.calculateOrder(
            List.of(100.0), List.of(1), "REGULAR", null);
        assertEquals(0.0,   summary.getDiscountAmount(), 0.001);
        assertEquals(10.0,  summary.getTax(),            0.001);
        assertEquals(110.0, summary.getFinalPrice(),     0.001);
    }

    @Test
    void testSave10DiscountCode() {
        OrderSummary summary = engine.calculateOrder(
            List.of(100.0), List.of(1), "REGULAR", "SAVE10");
        assertEquals(10.0, summary.getDiscountAmount(), 0.001);
        assertEquals(9.0,  summary.getTax(),            0.001);
        assertEquals(99.0, summary.getFinalPrice(),     0.001);
    }

    @Test
    void testSave20DiscountCode() {
        OrderSummary summary = engine.calculateOrder(
            List.of(100.0), List.of(1), "REGULAR", "SAVE20");
        assertEquals(88.0, summary.getFinalPrice(), 0.001);
    }

    @Test
    void testVipCustomerDiscount() {
        OrderSummary summary = engine.calculateOrder(
            List.of(100.0), List.of(1), "VIP", null);
        assertEquals(15.0, summary.getDiscountAmount(), 0.001);
        assertEquals(6.8,  summary.getTax(),            0.001);
        assertEquals(91.8, summary.getFinalPrice(),     0.001);
    }

    @Test
    void testVipWithDiscountCode() {
        OrderSummary summary = engine.calculateOrder(
            List.of(100.0), List.of(1), "VIP", "SAVE10");
        assertEquals(25.0, summary.getDiscountAmount(), 0.001);
        assertEquals(81.0, summary.getFinalPrice(),     0.001);
    }

    @Test
    void testMultipleItems() {
        OrderSummary summary = engine.calculateOrder(
            List.of(10.0, 5.0, 20.0), List.of(3, 4, 1), "REGULAR", null);
        assertEquals(70.0, summary.getSubtotal(),   0.001);
        assertEquals(77.0, summary.getFinalPrice(), 0.001);
    }

    @Test
    void testOrderSummaryToString() {
        OrderSummary summary = engine.calculateOrder(
            List.of(100.0), List.of(1), "REGULAR", null);
        assertTrue(summary.toString().contains("subtotal=100.00"));
    }
}