package com.senla.model;


import java.util.Objects;


public final class Room {
    private RoomStatus status;
    private OccupancyStatus occupancyStatus;


    private final String number;

    private double price;
    private int stars;
    private int capacity;


    public Room(String number, int capacity, int stars) {

        this.status = RoomStatus.AVAILABLE;
        this.occupancyStatus = OccupancyStatus.VACANT;
        this.number = number;
        //zthis.number = Objects.requireNonNull(number, "number");

        this.price = 0.0;
        this.capacity = capacity;
        this.stars = stars;
    }



    public RoomStatus status() { return status; }
    public OccupancyStatus occupancyStatus() { return occupancyStatus; }

    public String number() { return number; }

    public double price() { return price; }
    public int capacity() { return capacity; }
    public int stars() {return stars; }


    public void setStatus(RoomStatus status) { this.status = status; }
    public void setOccupancyStatus(OccupancyStatus occupancyStatus) { this.occupancyStatus = occupancyStatus; }

    public void setStars(int stars) { this.stars = stars; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setPrice(double price) { this.price = price; }
}