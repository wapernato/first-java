package com.senla.model;


public enum ReservationStatus {
    NEW,
    CONFIRMED,
    CHECKED_IN,
    CHECKED_OUT,
    CANCELLED,
    NO_SHOW;


    public static ReservationStatus fromString(String status) {
        return ReservationStatus.valueOf(status.toUpperCase());
    }

    public String toDatabaseString() {
        return this.name().toLowerCase();
    }
}