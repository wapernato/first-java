package com.senla.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.annotation.ConfigProperty;

public final class Room {

    private RoomStatus status;
    private OccupancyStatus occupancyStatus;
    private String number;
    private double price;
    private int stars;
    private int capacity;


    public Room() {}

    public Room(String number, int capacity, int stars, double price) {


        this.status = RoomStatus.AVAILABLE;
        this.occupancyStatus = OccupancyStatus.VACANT;
        this.number = number;

        this.price = price;
        this.capacity = capacity;
        this.stars = stars;
    }


    public RoomStatus getStatus() { return status; }
    public OccupancyStatus getOccupancyStatus() { return occupancyStatus; }
    public String getNumber() { return number; }
    public double getPrice() { return price; }
    public int getCapacity() { return capacity; }
    public int getStars() { return stars; }

    public void setNumber(String number){ this.number = number; }
    public void setStatus(RoomStatus status) { this.status = status; }
    public void setOccupancyStatus(OccupancyStatus occupancyStatus) { this.occupancyStatus = occupancyStatus; }
    public void setStars(int stars) { this.stars = stars; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setPrice(double price) { this.price = price; }
}