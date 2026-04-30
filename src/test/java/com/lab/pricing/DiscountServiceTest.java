package com.lab.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountServiceTest {

    private DiscountService service;

    @BeforeEach
    void setUp() {
        service = new DiscountService();
    }

    @Test
    void testNoDiscount() {
        double result = service.calculateDiscount(100.0, CustomerType.REGULAR, null);
        assertEquals(0.0, result, 0.001);
    }

    @Test
    void testSave5Code() {
        double result = service.calculateDiscount(100.0, CustomerType.REGULAR, "SAVE5");
        assertEquals(5.0, result, 0.001);
    }

    @Test
    void testSave10Code() {
        double result = service.calculateDiscount(100.0, CustomerType.REGULAR, "SAVE10");
        assertEquals(10.0, result, 0.001);
    }

    @Test
    void testSave20Code() {
        double result = service.calculateDiscount(100.0, CustomerType.REGULAR, "SAVE20");
        assertEquals(20.0, result, 0.001);
    }

    @Test
    void testVipDiscount() {
        double result = service.calculateDiscount(100.0, CustomerType.VIP, null);
        assertEquals(15.0, result, 0.001);
    }

    @Test
    void testVipPlusCode() {
        double result = service.calculateDiscount(100.0, CustomerType.VIP, "SAVE10");
        assertEquals(25.0, result, 0.001);
    }

    @Test
    void testUnknownCodeGivesZero() {
        double result = service.calculateDiscount(100.0, CustomerType.REGULAR, "INVALID");
        assertEquals(0.0, result, 0.001);
    }
}