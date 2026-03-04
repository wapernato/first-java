package com.senla.service;
import com.senla.model.forDAO.Hotel;


public interface HotelService {

    Hotel create(String city, String name, int stars);

}
