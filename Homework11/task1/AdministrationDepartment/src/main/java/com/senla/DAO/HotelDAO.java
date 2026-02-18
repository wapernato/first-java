package com.senla.DAO;

import com.senla.model.Hotel;

public interface HotelDAO {
    Hotel save(Hotel hotel);
    Hotel findById(Long id);
}
