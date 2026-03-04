package com.senla.service.impl;

import com.senla.model.forDAO.Hotel;
import com.senla.model.forDAO.HotelFactory;
import com.senla.service.HotelService;
import java.util.HashMap;
import java.util.Map;


public class InMemoryHotelService implements HotelService {

    long seq = 1;
    private final Map<Long, Hotel> hotels = new HashMap<>();


    @Override
    public Hotel create(String city, String name, int stars) {
        Hotel hotel = HotelFactory.create(city, name, stars);
        hotel.setHotelId(seq++);
        hotels.put(hotel.getHotelId(), hotel);
        return hotel;
    }



}
