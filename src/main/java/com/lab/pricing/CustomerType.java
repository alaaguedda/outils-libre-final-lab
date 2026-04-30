package com.lab.pricing;

public enum CustomerType {
    REGULAR,
    VIP;

    public static CustomerType from(String value) {
        if (value == null) return REGULAR;
        return switch (value.toUpperCase()) {
            case "VIP" -> VIP;
            default   -> REGULAR;
        };
    }
}