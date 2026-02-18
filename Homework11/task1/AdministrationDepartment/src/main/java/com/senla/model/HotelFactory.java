package com.senla.model;

public class HotelFactory {

    private HotelFactory() {}

    public static Hotel create(String city, String name, int stars) {
        return new Hotel(city, name, stars);
    }

}
