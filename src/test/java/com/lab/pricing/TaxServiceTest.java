package com.lab.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxServiceTest {

    private TaxService taxService;

    @BeforeEach
    void setUp() {
        taxService = new TaxService();
    }

    @Test
    void testRegularTax() {
        double tax = taxService.calculateTax(100.0, CustomerType.REGULAR);
        assertEquals(10.0, tax, 0.001);
    }

    @Test
    void testVipTax() {
        double tax = taxService.calculateTax(100.0, CustomerType.VIP);
        assertEquals(8.0, tax, 0.001);
    }

    @Test
    void testZeroAmountTax() {
        double tax = taxService.calculateTax(0.0, CustomerType.REGULAR);
        assertEquals(0.0, tax, 0.001);
    }
}