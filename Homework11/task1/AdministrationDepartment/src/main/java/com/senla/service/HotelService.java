package com.senla.service;
import com.senla.model.Hotel;

import java.util.List;


public interface HotelService {

    Hotel create(String city, String name, int stars);

}
