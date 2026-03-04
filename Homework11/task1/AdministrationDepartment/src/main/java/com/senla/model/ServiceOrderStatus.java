package com.senla.model;

public enum ServiceOrderStatus {
    ORDERED,
    PAID,
    CANCELLED,
    DELIVERED;

    public static ServiceOrderStatus fromString(String status) {
        return ServiceOrderStatus.valueOf(status.toUpperCase());
    }

    public String toDatabaseString() {
        return this.name().toLowerCase();
    }
}