package com.senla.model;


import java.util.Objects;


public final class Room {
    private final String number;
    private RoomStatus status;
    private double price;


    public Room(String number) {
        this.number = Objects.requireNonNull(number, "number");
        this.status = RoomStatus.AVAILABLE;
        this.price = 0.0;
    }


    public String number() { return number; }
    public RoomStatus status() { return status; }
    public double price() { return price; }


    public void setStatus(RoomStatus status) { this.status = status; }
    public void setPrice(double price) { this.price = price; }
}